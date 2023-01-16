package com.example.contract.config.exception;

import com.example.contract.enums.ErrorCode;
import lombok.Getter;

@Getter
final public class DataNotFoundException extends AppException {

    private final ErrorCode errorCode = ErrorCode.NOT_FOUND_DATA;

    public DataNotFoundException(String message) {
        super(message);
    }

}
