package io.clementleetimfu.educationmanagementsystem.constants;

import io.clementleetimfu.educationmanagementsystem.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorCodeEnumTest {

    @Test
    @DisplayName("Test Error Code Getter")
    void testErrorCodeGetter() {
        ErrorCodeEnum errorCode = ErrorCodeEnum.INVALID_CREDENTIALS;

        int code = errorCode.getCode();
        String message = errorCode.getMessage();

        assertEquals(1001, code);
        assertEquals("Invalid username or password", message);
    }

    @Test
    @DisplayName("Test Business Exception With Error Code")
    void testBusinessExceptionWithErrorCode() {
        ErrorCodeEnum errorCode = ErrorCodeEnum.INVALID_CREDENTIALS;
        BusinessException exception = new BusinessException(errorCode);

        assertNotNull(exception);
        assertEquals(errorCode.getCode(), exception.getCode());
        assertEquals(errorCode.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Test All Error Codes")
    void testAllErrorCodes() {
        ErrorCodeEnum[] values = ErrorCodeEnum.values();

        assertNotNull(values);
        assertTrue(values.length > 0);

        for (ErrorCodeEnum errorCode : values) {
            assertNotNull(errorCode);
            assertNotNull(errorCode.getCode());
            assertNotNull(errorCode.getMessage());
        }
    }

    @Test
    @DisplayName("Test Value Of")
    void testValueOf() {
        ErrorCodeEnum errorCode = ErrorCodeEnum.valueOf("INVALID_CREDENTIALS");

        assertEquals(ErrorCodeEnum.INVALID_CREDENTIALS, errorCode);
    }
}
