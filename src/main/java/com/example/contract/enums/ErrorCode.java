package com.example.contract.enums;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * 에러 코드
 */
@Getter
public enum ErrorCode {

    /**
     * 통합적인 서버에러 발생시
     */
    SERVER_ERROR("E001", "SERVER_ERROR")
    /**
     * 데이터를 찾지 못하는 케이스
     */
    , NOT_FOUND_DATA("E002", "Not found Data")

    /**
     * 요청된 값이 조건에 부합하지 않는 케이스
     */
    , BAD_REQUEST("E003", "Bad Request");

    private final String code;

    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 에러 메세지를 가공해주는 메소드 입니다.
     *
     * @param message Custom 메세지 입니다.
     * @return 에러 코드 메세지 : Custom 메세지 포멧으로 메세지를 리턴합니다.
     */
    public String convertMessage(@NotNull String message) {
        if (message.isEmpty()) {
            return this.getMessage();
        }
        return String.format("%s : %s", this.getMessage(), message);
    }
}
