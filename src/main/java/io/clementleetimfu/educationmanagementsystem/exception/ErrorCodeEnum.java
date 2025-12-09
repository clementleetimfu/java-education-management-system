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

    ACTIVITY_LOG_NOT_FOUND(5001, "Activity log not found"),

    CLAZZ_NOT_FOUND(6001, "Clazz not found"),
    CLAZZ_ADD_FAILED(6002, "Clazz add failed"),
    CLAZZ_UPDATE_FAILED(6003, "Clazz update failed"),
    CLAZZ_DELETE_FAILED(6004, "Clazz delete failed"),

    JOB_TITLE_NOT_FOUND(7001, "Job title not found"),

    SUBJECT_NOT_FOUND(8001, "Subject not found"),

    STUDENT_NOT_FOUND(9001, "Student not found"),
    STUDENT_ADD_FAILED(9002, "Student add failed"),
    STUDENT_DELETE_FAILED(9003, "Student delete failed"),
    STUDENT_UPDATE_FAILED(9004, "Student update failed"),

    STUDENT_NUMBER_SEQUENCE_ADD_FAILED(10001, "Student number sequence add failed"),
    STUDENT_NUMBER_SEQUENCE_NOT_FOUND(10002, "Student number sequence not found"),

    EDUCATION_LEVEL_NOT_FOUND(11001, "Education level not found");

    private final int code;
    private final String message;

}
