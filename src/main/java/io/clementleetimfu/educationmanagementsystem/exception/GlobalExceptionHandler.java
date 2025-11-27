package io.clementleetimfu.educationmanagementsystem.exception;

import io.clementleetimfu.educationmanagementsystem.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.warn("Exception:{}", e.getMessage(), e);
        return Result.fail(e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e) {
        log.warn("BusinessException code:{}, message:{}", e.getCode(), e.getMessage(), e);
        return Result.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Result<Void> handleDuplicateKey(DuplicateKeyException e) {
        log.warn("DuplicateKeyException:{}", e.getMessage(), e);
        String message = e.getMessage();
        String errMsg = message.substring(message.indexOf("Duplicate entry"));
        String[] errMsgArr = errMsg.split(" ");
        return Result.fail(errMsgArr[2] + "already exists");
    }

}