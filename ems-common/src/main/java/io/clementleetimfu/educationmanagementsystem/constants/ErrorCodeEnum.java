package io.clementleetimfu.educationmanagementsystem.constants;

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
    DEPARTMENT_DELETE_NOT_ALLOWED(2004, "Department still has employees"),

    EMPLOYEE_NOT_FOUND(3001, "Employee not found"),
    EMPLOYEE_ADD_FAILED(3002, "Employee add failed"),
    EMPLOYEE_DELETE_FAILED(3002, "Employee delete failed"),
    EMPLOYEE_UPDATE_FAILED(3002, "Employee update failed"),

    WORK_EXPERIENCE_ADD_FAILED(4002, "Work experience add failed"),
    WORK_EXPERIENCE_DELETE_FAILED(4002, "Work experience delete failed"),

    ACTIVITY_LOG_NOT_FOUND(5001, "Activity log not found"),

    CLAZZ_NOT_FOUND(6001, "Class not found"),
    CLAZZ_ADD_FAILED(6002, "Class add failed"),
    CLAZZ_UPDATE_FAILED(6003, "Class update failed"),
    CLAZZ_DELETE_FAILED(6004, "Class delete failed"),
    CLAZZ_DELETE_NOT_ALLOWED(6005, "Class still has students"),

    JOB_TITLE_NOT_FOUND(7001, "Job title not found"),

    SUBJECT_NOT_FOUND(8001, "Subject not found"),

    STUDENT_NOT_FOUND(9001, "Student not found"),
    STUDENT_ADD_FAILED(9002, "Student add failed"),
    STUDENT_DELETE_FAILED(9003, "Student delete failed"),
    STUDENT_UPDATE_FAILED(9004, "Student update failed"),

    STUDENT_NUMBER_SEQUENCE_ADD_FAILED(10001, "Student number sequence add failed"),
    STUDENT_NUMBER_SEQUENCE_UPDATE_FAILED(10002, "Student number sequence update failed"),

    EDUCATION_LEVEL_NOT_FOUND(11001, "Education level not found"),

    PERMISSION_DENIED(12001, "Permission denied"),

    RAW_PASSWORD_IS_NULL(13001, "Raw password is null");

    private final int code;
    private final String message;

}
