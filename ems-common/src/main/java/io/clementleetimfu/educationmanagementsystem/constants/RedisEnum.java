package io.clementleetimfu.educationmanagementsystem.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RedisEnum {
    BLACKLIST_TOKEN_PREFIX("blacklistToken:");

    private final String value;
}
