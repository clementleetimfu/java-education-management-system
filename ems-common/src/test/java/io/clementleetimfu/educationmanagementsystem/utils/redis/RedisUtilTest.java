package io.clementleetimfu.educationmanagementsystem.utils.redis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RedisUtilTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @InjectMocks
    private RedisUtil redisUtil;

    @BeforeEach
    void setUp() {
        lenient().when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    @DisplayName("Test Get Value")
    void testGetValue() {
        String key = "test-key";
        Object expectedValue = "test-value";
        when(valueOperations.get(key)).thenReturn(expectedValue);

        Object result = redisUtil.getValue(key);

        assertEquals(expectedValue, result);
        verify(valueOperations, times(1)).get(key);
    }

    @Test
    @DisplayName("Test Get Value Returns Null")
    void testGetValueReturnsNull() {
        String key = "non-existent-key";
        when(valueOperations.get(key)).thenReturn(null);

        Object result = redisUtil.getValue(key);

        assertNull(result);
        verify(valueOperations, times(1)).get(key);
    }

    @Test
    @DisplayName("Test Set Value")
    void testSetValue() {
        String key = "test-key";
        Object value = "test-value";

        redisUtil.setValue(key, value);

        verify(valueOperations, times(1)).set(key, value);
    }

    @Test
    @DisplayName("Test Set Value With Timeout")
    void testSetValueWithTimeout() {
        String key = "test-key";
        Object value = "test-value";
        long timeout = 60L;
        TimeUnit unit = TimeUnit.SECONDS;

        redisUtil.setValue(key, value, timeout, unit);

        verify(valueOperations, times(1)).set(key, value, timeout, unit);
    }

    @Test
    @DisplayName("Test Is Token Blacklisted Token Found")
    void testIsTokenBlacklistedTokenFound() {
        String key = "blacklistToken:blacklisted-token";
        String token = "blacklisted-token";

        when(redisTemplate.hasKey(key)).thenReturn(Boolean.TRUE);

        Boolean result = redisUtil.isTokenBlacklisted(token);

        assertTrue(result);
        verify(redisTemplate, times(1)).hasKey(key);
    }

    @Test
    @DisplayName("Test Is Token Blacklisted Token Not Found")
    void testIsTokenBlacklistedTokenNotFound() {
        String key = "blacklistToken:not-blacklisted-token";
        String token = "not-blacklisted-token";

        when(redisTemplate.hasKey(key)).thenReturn(Boolean.FALSE);

        Boolean result = redisUtil.isTokenBlacklisted(token);

        assertFalse(result);
        verify(redisTemplate, times(1)).hasKey(key);
    }

    @Test
    @DisplayName("Test Set Value With Null Key")
    void testSetValueWithNullKey() {
        String key = null;
        Object value = "test-value";

        doThrow(new IllegalArgumentException("Key cannot be null")).when(valueOperations).set(key, value);

        assertThrows(IllegalArgumentException.class, () -> redisUtil.setValue(key, value));
    }

}