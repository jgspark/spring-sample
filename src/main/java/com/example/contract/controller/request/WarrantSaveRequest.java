package com.example.contract.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class WarrantSaveRequest {

    @NotNull
    private String title;

    @NotNull
    private BigDecimal subscriptionAmount;

    @NotNull
    private BigDecimal standardAmount;

}
