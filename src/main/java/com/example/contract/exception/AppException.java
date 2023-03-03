package com.example.contract.exception;

import lombok.Getter;

/**
 * 어플리케이션 공통 예외 클래스
 *
 * @see DataNotFoundException
 */
@Getter
public class AppException extends RuntimeException {

    /**
     * 에러 코드
     */
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
