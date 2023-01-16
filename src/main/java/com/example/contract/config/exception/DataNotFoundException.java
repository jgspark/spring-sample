package com.example.contract.config.exception;

import com.example.contract.enums.ErrorCode;

final public class DataNotFoundException extends AppException {

    public DataNotFoundException(String message) {
        super(ErrorCode.NOT_FOUND_DATA, message);
    }

}
