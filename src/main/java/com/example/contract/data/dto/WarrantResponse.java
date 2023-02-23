package com.example.contract.data.dto;

import com.example.contract.data.doamin.Warrant;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class WarrantResponse {

    private final Long id;

    private final String title;

    private final BigDecimal subscriptionAmount;

    private final BigDecimal standardAmount;

    public WarrantResponse(Warrant warrant) {
        this.id = warrant.getId();
        this.title = warrant.getTitle();
        this.subscriptionAmount = warrant.getSubscriptionAmount();
        this.standardAmount = warrant.getStandardAmount();
    }
}
