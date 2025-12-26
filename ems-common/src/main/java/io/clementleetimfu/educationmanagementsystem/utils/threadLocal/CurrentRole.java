package io.clementleetimfu.educationmanagementsystem.utils.threadLocal;

public class CurrentRole {
    private static final ThreadLocal<String> ROLE_NAME = new ThreadLocal<>();

    public static String get() {
        return ROLE_NAME.get();
    }

    public static void set(String roleName) {
        ROLE_NAME.set(roleName);
    }

    public static void remove() {
        ROLE_NAME.remove();
    }
}
