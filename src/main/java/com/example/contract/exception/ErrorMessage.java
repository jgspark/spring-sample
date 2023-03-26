package com.example.contract.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public record ErrorMessage(String code, String message) {
}
