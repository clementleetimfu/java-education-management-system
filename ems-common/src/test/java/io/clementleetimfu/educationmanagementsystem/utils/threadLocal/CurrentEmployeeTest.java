package io.clementleetimfu.educationmanagementsystem.utils.threadLocal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrentEmployeeTest {

    @AfterEach
    void tearDown() {
        CurrentEmployee.remove();
    }

    @Test
    @DisplayName("Test Set And Get Employee Id")
    void testSetAndGetEmployeeId() {
        Integer employeeId = 123;

        CurrentEmployee.set(employeeId);
        Integer result = CurrentEmployee.get();

        assertEquals(employeeId, result);
    }

    @Test
    @DisplayName("Test Get Employee Id When Not Set")
    void testGetEmployeeIdWhenNotSet() {
        Integer result = CurrentEmployee.get();
        assertNull(result);
    }

    @Test
    @DisplayName("Test Remove Employee Id")
    void testRemoveEmployeeId() {
        Integer employeeId = 123;

        CurrentEmployee.set(employeeId);
        assertNotNull(CurrentEmployee.get());

        CurrentEmployee.remove();
        assertNull(CurrentEmployee.get());
    }

    @Test
    @DisplayName("Test Set Multiple Employee Ids")
    void testSetMultipleEmployeeIds() {
        Integer employeeId1 = 100;
        Integer employeeId2 = 200;

        CurrentEmployee.set(employeeId1);
        assertEquals(employeeId1, CurrentEmployee.get());

        CurrentEmployee.set(employeeId2);
        assertEquals(employeeId2, CurrentEmployee.get());
    }

    @Test
    @DisplayName("Test Remove When Not Set")
    void testRemoveWhenNotSet() {
        assertDoesNotThrow(() -> CurrentEmployee.remove());
        assertNull(CurrentEmployee.get());
    }

    @Test
    @DisplayName("Test Set Null Employee Id")
    void testSetNullEmployeeId() {
        CurrentEmployee.set(null);

        assertNull(CurrentEmployee.get());
    }
}