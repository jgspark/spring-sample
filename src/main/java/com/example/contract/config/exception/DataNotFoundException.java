package com.example.contract.config.exception;

import com.example.contract.data.enums.ErrorCode;

/**
 * 데이터를 찾지 못할 때 사용 하는 예외
 */
final public class DataNotFoundException extends AppException {

    /**
     * @param message response 에 전달할 메세지
     */
    public DataNotFoundException(String message) {
        super(ErrorCode.NOT_FOUND_DATA, message);
    }
}
