package com.example.contract.config.exception;

import com.example.contract.enums.ErrorCode;
import com.example.contract.web.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppErrorHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorMessage handler(Exception e) {
        ErrorCode errorCode = ErrorCode.SERVER_ERROR;
        e.printStackTrace();
        return new ErrorMessage(errorCode.getCode(), errorCode.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorMessage handler(MethodArgumentNotValidException e) {
        ErrorCode errorCode = ErrorCode.BAD_REQUEST;
        e.printStackTrace();
        return new ErrorMessage(errorCode.getCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(AppException.class)
    public ErrorMessage handler(AppException e) {
        ErrorCode errorCode = ErrorCode.SERVER_ERROR;
        e.printStackTrace();
        return new ErrorMessage(errorCode.getCode(), convertMessage(errorCode, e.getMessage()));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
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
