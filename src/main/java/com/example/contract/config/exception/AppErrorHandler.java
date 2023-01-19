package com.example.contract.config.exception;

import com.example.contract.enums.ErrorCode;
import com.example.contract.web.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 에러 핸들러 클래스
 *
 * @see Exception
 * @see MethodArgumentNotValidException
 * @see AppException
 * @see DataNotFoundException
 */
@RestControllerAdvice
public class AppErrorHandler {

    /**
     * Exception 을 처리 하기 위한 메소드
     * ResponseStatus의 경우 500 으로 정의
     *
     * @param e {@link Exception}
     * @return 에러 메세지 {@link ErrorMessage}, {@link ErrorCode}
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorMessage handler(Exception e) {
        ErrorCode errorCode = ErrorCode.SERVER_ERROR;
        e.printStackTrace();
        return new ErrorMessage(errorCode.getCode(), errorCode.getMessage());
    }


    /**
     * MethodArgumentNotValidException 을 처리 하기 위한 메소드
     * ResponseStatus의 경우 400 으로 정의
     *
     * @param e {@link MethodArgumentNotValidException}
     * @return 에러 메세지 {@link ErrorMessage}, {@link ErrorCode}
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorMessage handler(MethodArgumentNotValidException e) {
        ErrorCode errorCode = ErrorCode.BAD_REQUEST;
        e.printStackTrace();
        return new ErrorMessage(errorCode.getCode(), e.getMessage());
    }

    /**
     * AppException 을 처리 하기 위한 메소드
     * ResponseStatus의 경우 500 으로 정의
     *
     * @param e {@link AppException}
     * @return 에러 메세지 {@link ErrorMessage}, {@link ErrorCode}
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(AppException.class)
    public ErrorMessage handler(AppException e) {
        ErrorCode errorCode = ErrorCode.SERVER_ERROR;
        e.printStackTrace();
        return new ErrorMessage(errorCode.getCode(), errorCode.convertMessage(e.getMessage()));
    }

    /**
     * DataNotFoundException 을 처리 하기 위한 메소드
     * ResponseStatus의 경우 204 으로 정의
     *
     * @param e {@link DataNotFoundException}
     * @return 에러 메세지 {@link ErrorMessage}, {@link ErrorCode}
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(DataNotFoundException.class)
    public ErrorMessage handler(DataNotFoundException e) {
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ErrorMessage(errorCode.getCode(), errorCode.convertMessage(e.getMessage()));
    }

}
