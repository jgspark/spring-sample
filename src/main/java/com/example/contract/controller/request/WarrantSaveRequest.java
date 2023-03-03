package com.example.contract.controller.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;
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
