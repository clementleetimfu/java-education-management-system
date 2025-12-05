package io.clementleetimfu.educationmanagementsystem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {

    INVALID_CREDENTIALS(1001, "Invalid username or password"),

    DEPARTMENT_NOT_FOUND(2001, "Department not found"),
    DEPARTMENT_DELETE_FAILED(2002, "Department delete failed"),
    DEPARTMENT_ADD_FAILED(2003, "Department add failed"),
    DEPARTMENT_UPDATE_FAILED(2003, "Department update failed"),

    EMPLOYEE_NOT_FOUND(3001, "Employee not found"),
    EMPLOYEE_ADD_FAILED(3002, "Employee add failed"),
    EMPLOYEE_DELETE_FAILED(3002, "Employee delete failed"),
    EMPLOYEE_UPDATE_FAILED(3002, "Employee update failed"),

    WORK_EXPERIENCE_ADD_FAILED(4002, "Work experience add failed"),
    WORK_EXPERIENCE_DELETE_FAILED(4002, "Work experience delete failed"),

    ACTIVITY_LOG_NOT_FOUND(5001, "Activity log not found");

    private final int code;
    private final String message;

}
