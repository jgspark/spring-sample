package com.example.contract.enums;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public enum ErrorCode {

    SERVER_ERROR("E001", "SERVER_ERROR")
    , NOT_FOUND_DATA("E002", "Not found Data")
    , BAD_REQUEST("E003", "Bad Request");

    private final String code;

    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String convertMessage(@NotNull String message) {
        if (message.isEmpty()) {
            return this.getMessage();
        }
        return String.format("%s : %s", this.getMessage(), message);
    }
}
