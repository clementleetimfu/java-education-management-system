package io.clementleetimfu.educationmanagementsystem.service.impl;

import io.clementleetimfu.educationmanagementsystem.constants.ErrorCodeEnum;
import io.clementleetimfu.educationmanagementsystem.exception.BusinessException;
import io.clementleetimfu.educationmanagementsystem.mapper.EmployeeMapper;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.auth.LoginDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.auth.UpdatePasswordDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.Employee;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.auth.LoginVO;
import io.clementleetimfu.educationmanagementsystem.utils.bcrypt.BcryptUtil;
import io.clementleetimfu.educationmanagementsystem.utils.jwt.JwtUtil;
import io.clementleetimfu.educationmanagementsystem.utils.redis.RedisUtil;
import io.clementleetimfu.educationmanagementsystem.utils.threadLocal.CurrentEmployee;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Auth Service Implementation Tests")
class AuthServiceImplTest {

    @Mock
    private EmployeeMapper employeeMapper;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private BcryptUtil bcryptUtil;

    @Mock
    private RedisUtil redisUtil;

    @InjectMocks
    private AuthServiceImpl authServiceImpl;

    @Mock
    private Claims claims;

    private MockedStatic<CurrentEmployee> mockedCurrentEmployee;


    @BeforeEach
    void setUp() {
        mockedCurrentEmployee = mockStatic(CurrentEmployee.class);
    }

    @AfterEach
    void tearDown() {
        if (mockedCurrentEmployee != null) {
            mockedCurrentEmployee.close();
        }
    }

    @Test
    @DisplayName("Test Login Valid Credentials Returns Token")
    void testLoginValidCredentialsReturnsToken() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("admin");
        loginDTO.setPassword("password");

        LoginVO loginVO = createLoginVO(1, "ROLE_ADMIN", "hashedPassword", Boolean.TRUE);
        String expectedToken = "jwt.token.here";

        when(employeeMapper.selectEmployeeByUsername("admin")).thenReturn(loginVO);
        when(bcryptUtil.verify("password", "hashedPassword")).thenReturn(true);
        when(jwtUtil.generateToken(any(Map.class))).thenReturn(expectedToken);

        LoginVO result = authServiceImpl.login(loginDTO);

        assertNotNull(result);
        assertEquals(expectedToken, result.getToken());
        verify(jwtUtil, times(1)).generateToken(any(Map.class));
    }

    @Test
    @DisplayName("Test Login User Not Found Throws Exception")
    void testLoginUserNotFoundThrowsException() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("nonexistent");
        loginDTO.setPassword("password");

        when(employeeMapper.selectEmployeeByUsername("nonexistent")).thenReturn(null);

        BusinessException exception = assertThrows(BusinessException.class, () -> authServiceImpl.login(loginDTO));
        assertEquals(ErrorCodeEnum.INVALID_CREDENTIALS.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.INVALID_CREDENTIALS.getMessage(), exception.getMessage());

        verify(bcryptUtil, never()).verify(anyString(), anyString());
        verify(jwtUtil, never()).generateToken(any());
    }

    @Test
    @DisplayName("Test Login Invalid Password Throws Exception")
    void testLoginInvalidPasswordThrowsException() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("admin");
        loginDTO.setPassword("wrongpassword");

        LoginVO loginVO = createLoginVO(1, "ROLE_ADMIN", "hashedPassword", Boolean.TRUE);

        when(employeeMapper.selectEmployeeByUsername("admin")).thenReturn(loginVO);
        when(bcryptUtil.verify("wrongpassword", "hashedPassword")).thenReturn(false);

        BusinessException exception = assertThrows(BusinessException.class, () -> authServiceImpl.login(loginDTO));
        assertEquals(ErrorCodeEnum.INVALID_CREDENTIALS.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.INVALID_CREDENTIALS.getMessage(), exception.getMessage());

        verify(jwtUtil, never()).generateToken(any());
    }

    @Test
    @DisplayName("Test Update Password Valid Password Returns True")
    void testUpdatePasswordValidPasswordReturnsTrue() {
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO();
        updatePasswordDTO.setId(1);
        updatePasswordDTO.setPassword("newPassword");

        String hashedPassword = "newHashedPassword";
        when(bcryptUtil.hash("newPassword")).thenReturn(hashedPassword);
        when(employeeMapper.updateEmployee(any(Employee.class))).thenReturn(1);

        Boolean result = authServiceImpl.updatePassword(updatePasswordDTO);

        assertTrue(result);
        verify(employeeMapper, times(1)).updateEmployee(any(Employee.class));
    }

    @Test
    @DisplayName("Test Update Password Update Failed Throws Exception")
    void testUpdatePasswordUpdateFailedThrowsException() {
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO();
        updatePasswordDTO.setId(1);
        updatePasswordDTO.setPassword("newPassword");

        when(bcryptUtil.hash("newPassword")).thenReturn("hashedPassword");
        when(employeeMapper.updateEmployee(any(Employee.class))).thenReturn(0);

        BusinessException exception = assertThrows(BusinessException.class, () -> authServiceImpl.updatePassword(updatePasswordDTO));
        assertEquals(ErrorCodeEnum.EMPLOYEE_UPDATE_FAILED.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.EMPLOYEE_UPDATE_FAILED.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Test Logout Token Blacklisted")
    void testLogoutTokenBlacklisted() {
        String authHeader = "Bearer jwt.token.here";
        String token = "jwt.token.here";
        Integer employeeId = 1;

        mockedCurrentEmployee.when(CurrentEmployee::get).thenReturn(employeeId);
        when(jwtUtil.parseToken(token)).thenReturn(claims);
        when(claims.getExpiration()).thenReturn(new Date(System.currentTimeMillis() + 3600000));

        authServiceImpl.logout(authHeader);

        verify(redisUtil, times(1)).setValue(eq("blacklistToken:1"), eq(token), anyLong(), eq(TimeUnit.MILLISECONDS));
    }

    private LoginVO createLoginVO(Integer id, String roleName, String password, boolean isFirstLogged) {
        LoginVO loginVO = new LoginVO();
        loginVO.setId(id);
        loginVO.setUsername("admin");
        loginVO.setPassword(password);
        loginVO.setRoleName(roleName);
        loginVO.setIsFirstLogged(isFirstLogged);
        return loginVO;
    }
}
