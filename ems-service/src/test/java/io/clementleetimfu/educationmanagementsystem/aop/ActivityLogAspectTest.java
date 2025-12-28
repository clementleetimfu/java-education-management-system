package io.clementleetimfu.educationmanagementsystem.aop;

import io.clementleetimfu.educationmanagementsystem.pojo.entity.ActivityLog;
import io.clementleetimfu.educationmanagementsystem.service.ActivityLogService;
import io.clementleetimfu.educationmanagementsystem.utils.threadLocal.CurrentEmployee;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Activity Log Aspect Tests")
class ActivityLogAspectTest {

    @Mock
    private ActivityLogService activityLogService;

    @InjectMocks
    private ActivityLogAspect activityLogAspect;

    @Mock
    private ProceedingJoinPoint pjp;

    @Mock
    private Signature signature;

    private MockedStatic<CurrentEmployee> mockedStaticCurrentEmployee;

    @BeforeEach
    void setUp() {
        mockedStaticCurrentEmployee = mockStatic(CurrentEmployee.class);
    }

    @AfterEach
    void tearDown() {
        if (mockedStaticCurrentEmployee != null) {
            mockedStaticCurrentEmployee.close();
        }
    }

    @Test
    @DisplayName("Test Add Activity Log Success")
    void testAddActivityLogSuccess() throws Throwable {
        Integer employeeId = 1;
        String methodName = "testMethod";
        Object[] args = new Object[]{"arg1", "arg2"};
        String returnType = "Object";

        mockedStaticCurrentEmployee.when(CurrentEmployee::get).thenReturn(employeeId);

        when(pjp.getTarget()).thenReturn(mock(Object.class));
        when(pjp.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn(methodName);
        when(pjp.getArgs()).thenReturn(args);
        when(pjp.proceed()).thenReturn(returnType);
        when(activityLogService.addActivityLog(any(ActivityLog.class))).thenReturn(Boolean.TRUE);

        Object result = activityLogAspect.addActivityLog(pjp);

        assertEquals(returnType, result);
        verify(activityLogService, times(1)).addActivityLog(any(ActivityLog.class));
        mockedStaticCurrentEmployee.verify(CurrentEmployee::get, times(1));
    }

    @Test
    @DisplayName("Test Add Activity Log With Exception")
    void testAddActivityLogWithException() throws Throwable {
        Integer employeeId = 1;
        String methodName = "testMethod";
        Object[] args = new Object[]{};
        RuntimeException expectedException = new RuntimeException("Test exception");

        mockedStaticCurrentEmployee.when(CurrentEmployee::get).thenReturn(employeeId);
        when(pjp.getTarget()).thenReturn(mock(Object.class));
        when(pjp.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn(methodName);
        when(pjp.getArgs()).thenReturn(args);
        when(pjp.proceed()).thenThrow(expectedException);
        when(activityLogService.addActivityLog(any(ActivityLog.class))).thenReturn(Boolean.TRUE);

        assertThrows(RuntimeException.class, () -> {
            activityLogAspect.addActivityLog(pjp);
        });

        verify(activityLogService, times(1)).addActivityLog(any(ActivityLog.class));
        mockedStaticCurrentEmployee.verify(CurrentEmployee::get, times(1));
    }

    @Test
    @DisplayName("Test Add Activity Log Null Result")
    void testAddActivityLogNullResult() throws Throwable {
        Integer employeeId = 1;
        String methodName = "testMethod";
        Object[] args = new Object[]{};

        mockedStaticCurrentEmployee.when(CurrentEmployee::get).thenReturn(employeeId);
        when(pjp.getTarget()).thenReturn(mock(Object.class));
        when(pjp.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn(methodName);
        when(pjp.getArgs()).thenReturn(args);
        when(pjp.proceed()).thenReturn(null);
        when(activityLogService.addActivityLog(any(ActivityLog.class))).thenReturn(Boolean.TRUE);

        Object result = activityLogAspect.addActivityLog(pjp);

        assertNull(result);
        verify(activityLogService, times(1)).addActivityLog(any(ActivityLog.class));
        mockedStaticCurrentEmployee.verify(CurrentEmployee::get, times(1));
    }

    @Test
    @DisplayName("Test Add Activity Log Current Employee Null")
    void testAddActivityLogCurrentEmployeeNull() throws Throwable {
        Integer employeeId = null;
        String methodName = "testMethod";
        Object[] args = new Object[]{};
        String returnType = "Object";

        mockedStaticCurrentEmployee.when(CurrentEmployee::get).thenReturn(employeeId);
        when(pjp.getTarget()).thenReturn(mock(Object.class));
        when(pjp.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn(methodName);
        when(pjp.getArgs()).thenReturn(args);
        when(pjp.proceed()).thenReturn(returnType);
        when(activityLogService.addActivityLog(any(ActivityLog.class))).thenReturn(Boolean.TRUE);

        Object result = activityLogAspect.addActivityLog(pjp);

        assertEquals(returnType, result);
        verify(activityLogService, times(1)).addActivityLog(any(ActivityLog.class));
        mockedStaticCurrentEmployee.verify(CurrentEmployee::get, times(1));
    }
}
