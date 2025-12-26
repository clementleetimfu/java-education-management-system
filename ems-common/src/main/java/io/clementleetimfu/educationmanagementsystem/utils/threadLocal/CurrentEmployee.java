package io.clementleetimfu.educationmanagementsystem.utils.threadLocal;

public class CurrentEmployee {
    private static final ThreadLocal<Integer> EMPLOYEE_ID = new ThreadLocal<>();

    public static Integer get() {
        return EMPLOYEE_ID.get();
    }

    public static void set(Integer employeeId) {
        EMPLOYEE_ID.set(employeeId);
    }

    public static void remove() {
        EMPLOYEE_ID.remove();
    }
}