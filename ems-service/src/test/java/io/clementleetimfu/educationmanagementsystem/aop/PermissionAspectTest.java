package io.clementleetimfu.educationmanagementsystem.aop;

import io.clementleetimfu.educationmanagementsystem.annotation.Permission;
import io.clementleetimfu.educationmanagementsystem.constants.ErrorCodeEnum;
import io.clementleetimfu.educationmanagementsystem.constants.RoleEnum;
import io.clementleetimfu.educationmanagementsystem.exception.BusinessException;
import io.clementleetimfu.educationmanagementsystem.utils.threadLocal.CurrentRole;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Permission Aspect Tests")
class PermissionAspectTest {

    @Mock
    private ProceedingJoinPoint pjp;

    @Mock
    private MethodSignature methodSignature;

    @InjectMocks
    private PermissionAspect permissionAspect;

    private MockedStatic<CurrentRole> mockedCurrentRole;

    private static final String DUMMY_METHOD_NAME = "dummyMethod";

    @BeforeEach
    void setUp() {
        mockedCurrentRole = mockStatic(CurrentRole.class);
    }

    @AfterEach
    void tearDown() {
        if (mockedCurrentRole != null) {
            mockedCurrentRole.close();
        }
    }

    @Test
    @DisplayName("Test Check Permission Admin Role Authorized")
    void testCheckPermissionAdminRoleAuthorized() throws Throwable {
        String currentRole = "ROLE_ADMIN";
        String returnType = "Object";

        when(pjp.getSignature()).thenReturn(methodSignature);

        Method dummyMethod = this.getClass().getDeclaredMethod(DUMMY_METHOD_NAME);
        when(methodSignature.getMethod()).thenReturn(dummyMethod);

        mockedCurrentRole.when(CurrentRole::get).thenReturn(currentRole);

        when(pjp.proceed()).thenReturn(returnType);

        Object result = permissionAspect.checkPermission(pjp);

        assertEquals(returnType, result);
        verify(pjp, times(1)).proceed();
    }

    @Test
    @DisplayName("Test Check Permission Employee Role Denied")
    void testCheckPermissionEmployeeRoleDenied() throws Throwable {
        String currentRole = "ROLE_EMPLOYEE";

        when(pjp.getSignature()).thenReturn(methodSignature);

        Method dummyMethod = this.getClass().getDeclaredMethod(DUMMY_METHOD_NAME);
        when(methodSignature.getMethod()).thenReturn(dummyMethod);

        mockedCurrentRole.when(CurrentRole::get).thenReturn(currentRole);

        BusinessException exception = assertThrows(BusinessException.class, () -> permissionAspect.checkPermission(pjp));
        assertEquals(ErrorCodeEnum.PERMISSION_DENIED.getCode(), exception.getCode());

        verify(pjp, never()).proceed();
    }

    @Permission(role = RoleEnum.ROLE_ADMIN)
    @SuppressWarnings("unused")
    private void dummyMethod() {
    }
}
