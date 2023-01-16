package com.example.contract.config.exception;

import com.example.contract.enums.ErrorCode;
import lombok.Getter;

@Getter
public class AppException extends RuntimeException {

    private ErrorCode errorCode;

    public AppException() {

    }

    public AppException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(Throwable cause) {
        super(cause);
    }

    public AppException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AppException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}