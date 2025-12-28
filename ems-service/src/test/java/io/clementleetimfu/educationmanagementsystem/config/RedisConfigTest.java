package io.clementleetimfu.educationmanagementsystem.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Redis Config Tests")
class RedisConfigTest {

    @Mock
    private RedisConnectionFactory redisConnectionFactory;

    @Test
    void testRedisTemplateBeanCreation() {
        RedisConfig config = new RedisConfig();

        RedisTemplate<String, Object> template = config.redisTemplate(redisConnectionFactory);

        assertNotNull(template, "RedisTemplate should not be null");
        assertEquals(redisConnectionFactory, template.getConnectionFactory());
        assertTrue(template.getKeySerializer() instanceof StringRedisSerializer,
                "Key serializer should be StringRedisSerializer");
    }
}

