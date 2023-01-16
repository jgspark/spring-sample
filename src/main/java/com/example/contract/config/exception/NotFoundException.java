package com.example.contract.config.exception;

import com.example.contract.enums.ErrorCode;

/**
 * todo : add
 */
public class NotFoundException extends AppException {

    private final ErrorCode errorCode = ErrorCode.NOT_FOUND_DATA;

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
