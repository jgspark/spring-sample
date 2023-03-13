package com.example.contract.dto.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record WarrantSaveRequest(
        @NotNull
        String title,
        @NotNull
        BigDecimal subscriptionAmount,
        @NotNull
        BigDecimal standardAmount
) {
}
