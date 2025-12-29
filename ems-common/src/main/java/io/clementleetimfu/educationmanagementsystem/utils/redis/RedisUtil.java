package io.clementleetimfu.educationmanagementsystem.utils.redis;

import io.clementleetimfu.educationmanagementsystem.constants.RedisEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void setValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setValue(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    public Set<String> getKeys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    public Boolean isTokenBlacklisted(String token, Integer employeeId) {
        if (token == null || token.isBlank()) {
            return Boolean.FALSE;
        }

        String key = RedisEnum.BLACKLIST_TOKEN_PREFIX.getValue() + employeeId;

        if (redisTemplate.hasKey(key)) {
            return token.equals(redisTemplate.opsForValue().get(key));
        }

        return Boolean.FALSE;
    }

}