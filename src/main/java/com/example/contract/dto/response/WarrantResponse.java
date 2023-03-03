package com.example.contract.dto.response;

import com.example.contract.domain.warrant.Warrant;
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
