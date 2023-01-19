package com.example.contract.web.dto;

import com.example.contract.doamin.Warrant;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class WarrantResponse {

    private final Long id;

    private final BigDecimal subscriptionAmount;

    private final BigDecimal standardAmount;

    public WarrantResponse(Warrant warrant) {
        this.id = warrant.getId();
        this.subscriptionAmount = warrant.getSubscriptionAmount();
        this.standardAmount = warrant.getStandardAmount();
    }
}
