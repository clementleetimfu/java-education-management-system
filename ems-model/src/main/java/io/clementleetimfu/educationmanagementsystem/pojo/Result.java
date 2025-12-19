package io.clementleetimfu.educationmanagementsystem.pojo;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    private static final Integer SUCCESS_CODE = 0;
    private static final Integer FAIL_CODE = 1;
    private static final String SUCCESS_MESSAGE = "success";

    public static Result<Void> success() {
        Result<Void> result = new Result<>();
        result.setCode(SUCCESS_CODE);
        result.setMessage(SUCCESS_MESSAGE);
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(SUCCESS_CODE);
        result.setMessage(SUCCESS_MESSAGE);
        result.setData(data);
        return result;
    }

    public static Result<Void> fail(String message) {
        return fail(FAIL_CODE, message);
    }

    public static Result<Void> fail(Integer code, String message) {
        Result<Void> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}