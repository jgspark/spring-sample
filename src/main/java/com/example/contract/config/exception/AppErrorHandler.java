package com.example.contract.config.exception;

import com.example.contract.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppErrorHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorMessage handler(Exception e) {
        ErrorCode errorCode = ErrorCode.SERVER_ERROR;
        e.printStackTrace();
        return new ErrorMessage(errorCode.getCode(), errorCode.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataNotFoundException.class)
    public ErrorMessage handler(DataNotFoundException e) {
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ErrorMessage(errorCode.getCode(), convertMessage(errorCode, e.getMessage()));
    }

    private String convertMessage(ErrorCode code, String message) {
        return String.format("%s : %s", code.getMessage(), message);
    }
}
