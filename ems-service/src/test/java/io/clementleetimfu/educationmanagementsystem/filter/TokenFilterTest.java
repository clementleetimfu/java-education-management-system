package io.clementleetimfu.educationmanagementsystem.filter;

import io.clementleetimfu.educationmanagementsystem.utils.jwt.JwtUtil;
import io.clementleetimfu.educationmanagementsystem.utils.redis.RedisUtil;
import io.clementleetimfu.educationmanagementsystem.utils.threadLocal.CurrentEmployee;
import io.clementleetimfu.educationmanagementsystem.utils.threadLocal.CurrentRole;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Token Filter Tests")
class TokenFilterTest {

    private TokenFilter tokenFilter;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private RedisUtil redisUtil;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private Claims claims;

    private MockedStatic<CurrentEmployee> mockedCurrentEmployee;
    private MockedStatic<CurrentRole> mockedCurrentRole;

    @BeforeEach
    void setUp() {
        tokenFilter = new TokenFilter();

        ReflectionTestUtils.setField(tokenFilter, "jwtUtil", jwtUtil);
        ReflectionTestUtils.setField(tokenFilter, "redisUtil", redisUtil);

        mockedCurrentEmployee = mockStatic(CurrentEmployee.class);
        mockedCurrentRole = mockStatic(CurrentRole.class);
    }

    @AfterEach
    void tearDown() {
        mockedCurrentEmployee.close();
        mockedCurrentRole.close();
    }

    @ParameterizedTest
    @ValueSource(strings = {"/auth/login", "/auth/update-password"})
    @DisplayName("Test Whitelisted URL (Login) - Bypasses Checks")
    void testWhitelistedUrl(String uri) throws Exception {
        when(request.getRequestURI()).thenReturn(uri);

        tokenFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(redisUtil, never()).isTokenBlacklisted(anyString());
        verify(jwtUtil, never()).validateToken(anyString());
    }

    @Test
    @DisplayName("Test Missing Auth Header - Returns 401")
    void testMissingAuthHeader() throws Exception {
        when(request.getRequestURI()).thenReturn("/api/data");
        when(request.getHeader("Authorization")).thenReturn(null);

        tokenFilter.doFilter(request, response, filterChain);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    @DisplayName("Test Blacklisted Token - Returns 401")
    void testBlacklistedToken() throws Exception {
        String token = "bad-token";
        when(request.getRequestURI()).thenReturn("/api/data");
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(redisUtil.isTokenBlacklisted(token)).thenReturn(true);

        tokenFilter.doFilter(request, response, filterChain);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    @DisplayName("Test Invalid JWT - Returns 401")
    void testInvalidJwt() throws Exception {
        String token = "invalid-token";
        when(request.getRequestURI()).thenReturn("/api/data");
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(redisUtil.isTokenBlacklisted(token)).thenReturn(false);
        when(jwtUtil.validateToken(token)).thenReturn(false);

        tokenFilter.doFilter(request, response, filterChain);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    @DisplayName("Test Success - Sets ThreadLocal and Proceeds")
    void testSuccess() throws Exception {
        String token = "valid-token";
        when(request.getRequestURI()).thenReturn("/api/data");
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);

        when(redisUtil.isTokenBlacklisted(token)).thenReturn(false);
        when(jwtUtil.validateToken(token)).thenReturn(true);

        when(jwtUtil.parseToken(token)).thenReturn(claims);
        when(claims.get("id", Integer.class)).thenReturn(1);
        when(claims.get("roleName", String.class)).thenReturn("ROLE_ADMIN");

        tokenFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);

        mockedCurrentEmployee.verify(() -> CurrentEmployee.set(1));
        mockedCurrentRole.verify(() -> CurrentRole.set("ROLE_ADMIN"));

        mockedCurrentEmployee.verify(CurrentEmployee::remove);
        mockedCurrentRole.verify(CurrentRole::remove);
    }
}