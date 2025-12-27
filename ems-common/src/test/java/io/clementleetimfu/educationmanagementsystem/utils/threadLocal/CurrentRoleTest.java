package io.clementleetimfu.educationmanagementsystem.utils.threadLocal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrentRoleTest {

    @AfterEach
    void tearDown() {
        CurrentRole.remove();
    }

    @Test
    @DisplayName("Test Set And Get Role Name")
    void testSetAndGetRoleName() {
        String roleName = "ROLE_ADMIN";

        CurrentRole.set(roleName);
        String result = CurrentRole.get();

        assertEquals(roleName, result);
    }

    @Test
    @DisplayName("Test Get Role Name When Not Set")
    void testGetRoleNameWhenNotSet() {
        String result = CurrentRole.get();
        assertNull(result);
    }

    @Test
    @DisplayName("Test Remove Role Name")
    void testRemoveRoleName() {
        String roleName = "ROLE_EMPLOYEE";

        CurrentRole.set(roleName);
        assertNotNull(CurrentRole.get());

        CurrentRole.remove();
        assertNull(CurrentRole.get());
    }

    @Test
    @DisplayName("Test Remove When Not Set")
    void testRemoveWhenNotSet() {
        assertDoesNotThrow(() -> CurrentRole.remove());
        assertNull(CurrentRole.get());
    }

    @Test
    @DisplayName("Test Set Empty Role Name")
    void testSetEmptyRoleName() {
        String roleName = "";

        CurrentRole.set(roleName);
        String result = CurrentRole.get();

        assertEquals(roleName, result);
    }
}