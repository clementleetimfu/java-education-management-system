package io.clementleetimfu.educationmanagementsystem.utils.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private String secretKey;

    @BeforeEach
    void setUp() {
        SecretKey key = Keys.hmacShaKeyFor("test-secret-key-for-testing-purposes-only-123456789012".getBytes(StandardCharsets.UTF_8));
        secretKey = Base64.getEncoder().encodeToString(key.getEncoded());
        jwtUtil = new JwtUtil(secretKey);
    }

    @Test
    @DisplayName("Test Generate Token")
    void testGenerateToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "john");
        claims.put("roleName", "ROLE_ADMIN");

        String token = jwtUtil.generateToken(claims);

        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.split("\\.").length == 3);
    }

    @Test
    @DisplayName("Test Parse Token")
    void testParseToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "john");
        claims.put("roleName", "ROLE_ADMIN");

        String token = jwtUtil.generateToken(claims);
        Claims parsedClaims = jwtUtil.parseToken(token);

        assertNotNull(parsedClaims);
        assertEquals(1, parsedClaims.get("id"));
        assertEquals("john", parsedClaims.get("username"));
        assertEquals("ROLE_ADMIN", parsedClaims.get("roleName"));
        assertNotNull(parsedClaims.getIssuedAt());
        assertNotNull(parsedClaims.getExpiration());
    }

    @Test
    @DisplayName("Test Validate Valid Token")
    void testValidateValidToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "john");
        claims.put("roleName", "ROLE_ADMIN");

        String token = jwtUtil.generateToken(claims);

        assertTrue(jwtUtil.validateToken(token));
    }

    @Test
    @DisplayName("Test Validate Invalid Token")
    void testValidateInvalidToken() {
        String invalidToken = "invalid.token.string";

        assertFalse(jwtUtil.validateToken(invalidToken));
    }

    @Test
    @DisplayName("Test Validate Expired Token")
    void testValidateExpiredToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "john");
        claims.put("roleName", "ROLE_ADMIN");

        Date pastDate = new Date(System.currentTimeMillis() - 1000 * 60 * 60);

        String expiredToken = Jwts.builder()
                .issuedAt(new Date(pastDate.getTime() - 1000 * 60 * 60 * 2))
                .expiration(pastDate)
                .claims(claims)
                .signWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey)))
                .compact();

        assertFalse(jwtUtil.validateToken(expiredToken));
    }

    @Test
    @DisplayName("Test Validate Tampered Token")
    void testValidateTamperedToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "john");
        claims.put("roleName", "ROLE_ADMIN");

        String token = jwtUtil.generateToken(claims);
        String tamperedToken = token + "tampered";

        assertFalse(jwtUtil.validateToken(tamperedToken));
    }

    @Test
    @DisplayName("Test Parse Empty Token")
    void testParseEmptyToken() {
        assertThrows(Exception.class, () -> jwtUtil.parseToken(""));
    }

    @Test
    @DisplayName("Test Parse Null Token")
    void testParseNullToken() {
        assertThrows(Exception.class, () -> jwtUtil.parseToken(null));
    }

    @Test
    @DisplayName("Test Generate Token With Empty Claims")
    void testGenerateTokenWithEmptyClaims() {
        Map<String, Object> claims = new HashMap<>();
        String token = jwtUtil.generateToken(claims);

        assertNotNull(token);
        assertTrue(jwtUtil.validateToken(token));
    }

    @Test
    @DisplayName("Test Token Expiration Time")
    void testTokenExpirationTime() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "john");
        claims.put("roleName", "ROLE_ADMIN");

        String token = jwtUtil.generateToken(claims);
        Claims parsedClaims = jwtUtil.parseToken(token);

        Date issuedAt = parsedClaims.getIssuedAt();
        Date expiration = parsedClaims.getExpiration();

        long difference = expiration.getTime() - issuedAt.getTime();
        long expectedDuration = 60 * 60 * 1000L;

        assertEquals(expectedDuration, difference);
    }

    @Test
    @DisplayName("Test Multiple Tokens Are Unique")
    void testMultipleTokensAreUnique() throws InterruptedException {
        Map<String, Object> claims1 = new HashMap<>();
        claims1.put("id", 1);

        Map<String, Object> claims2 = new HashMap<>();
        claims2.put("id", 2);

        String token1 = jwtUtil.generateToken(claims1);
        String token2 = jwtUtil.generateToken(claims2);

        assertNotEquals(token1, token2);
    }
}
