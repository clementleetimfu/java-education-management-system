package io.clementleetimfu.educationmanagementsystem.constants;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RoleEnumTest {

    @Test
    @DisplayName("Test Role Admin")
    void testRoleAdmin() {
        RoleEnum roleEnum = RoleEnum.ROLE_ADMIN;

        assertNotNull(roleEnum);
        assertEquals("ROLE_ADMIN", roleEnum.getValue());
    }

    @Test
    @DisplayName("Test Role Employee")
    void testRoleEmployee() {
        RoleEnum roleEnum = RoleEnum.ROLE_EMPLOYEE;

        assertNotNull(roleEnum);
        assertEquals("ROLE_EMPLOYEE", roleEnum.getValue());
    }

    @Test
    @DisplayName("Test Enum Values")
    void testEnumValues() {
        RoleEnum[] values = RoleEnum.values();

        assertEquals(RoleEnum.ROLE_ADMIN, values[0]);
        assertEquals(RoleEnum.ROLE_EMPLOYEE, values[1]);
    }

    @Test
    @DisplayName("Test Value Of")
    void testValueOf() {
        assertEquals(RoleEnum.ROLE_ADMIN, RoleEnum.valueOf("ROLE_ADMIN"));
        assertEquals(RoleEnum.ROLE_EMPLOYEE, RoleEnum.valueOf("ROLE_EMPLOYEE"));
    }
}
