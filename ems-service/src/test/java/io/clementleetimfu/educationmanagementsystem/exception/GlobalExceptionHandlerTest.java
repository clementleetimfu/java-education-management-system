package io.clementleetimfu.educationmanagementsystem.exception;

import io.clementleetimfu.educationmanagementsystem.constants.ErrorCodeEnum;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.result.Result;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@DisplayName("Global Exception Handler Tests")
class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    @DisplayName("Test Generic Exception Handler")
    void testHandleGenericException() {
        String errorMsg = "Unexpected system error";
        Exception e = new RuntimeException(errorMsg);

        Result<Void> result = handler.handleException(e);

        assertNotNull(result);
        assertEquals(errorMsg, result.getMessage());
    }

    @Test
    @DisplayName("Test Business Exception Handler")
    void testHandleBusinessException() {
        ErrorCodeEnum errorEnum = ErrorCodeEnum.EMPLOYEE_NOT_FOUND;
        BusinessException e = new BusinessException(errorEnum);

        Result<Void> result = handler.handleBusinessException(e);

        assertNotNull(result);
        assertEquals(errorEnum.getCode(), result.getCode());
        assertEquals(errorEnum.getMessage(), result.getMessage());
    }

    @Test
    @DisplayName("Test Duplicate Key Exception Handler")
    void testHandleDuplicateKey() {
        String sqlErrorMessage = "SQL Error: Duplicate entry 'test_user' for key 'users.username'";
        DuplicateKeyException e = new DuplicateKeyException(sqlErrorMessage);

        Result<Void> result = handler.handleDuplicateKey(e);

        assertNotNull(result);

        assertEquals("'test_user'already exists", result.getMessage());
    }
}
