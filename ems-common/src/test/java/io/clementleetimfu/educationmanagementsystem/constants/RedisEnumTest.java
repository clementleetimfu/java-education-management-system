package io.clementleetimfu.educationmanagementsystem.constants;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RedisEnumTest {

    @Test
    @DisplayName("Test Blacklist Token Prefix")
    void testBlacklistTokenPrefix() {
        RedisEnum redisEnum = RedisEnum.BLACKLIST_TOKEN_PREFIX;

        assertNotNull(redisEnum);
        assertEquals("blacklistToken:", redisEnum.getValue());
    }
}
