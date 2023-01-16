package com.example.contract.web.dto;

import com.example.contract.doamin.Warrant;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class WarrantResponse {

    private Long id;

    private BigDecimal subscriptionAmount;

    private BigDecimal standardAmount;

    public WarrantResponse(Warrant warrant) {
        this.id = warrant.getId();
        this.subscriptionAmount = warrant.getSubscriptionAmount();
        this.standardAmount = warrant.getStandardAmount();
    }
}
