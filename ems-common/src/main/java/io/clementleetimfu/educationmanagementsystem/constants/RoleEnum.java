package io.clementleetimfu.educationmanagementsystem.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleEnum {

    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_EMPLOYEE("ROLE_EMPLOYEE");

    private final String value;
}