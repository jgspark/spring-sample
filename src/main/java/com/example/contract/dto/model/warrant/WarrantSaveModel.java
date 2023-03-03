package com.example.contract.dto.model.warrant;

import com.example.contract.controller.request.WarrantSaveRequest;
import com.example.contract.domain.warrant.Warrant;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class WarrantSaveModel {

    private final String title;

    private final BigDecimal subscriptionAmount;

    private final BigDecimal standardAmount;

    public WarrantSaveModel(WarrantSaveRequest req) {
        this.title = req.getTitle();
        this.subscriptionAmount = req.getSubscriptionAmount();
        this.standardAmount = req.getStandardAmount();
    }

    public Warrant toEntity() {
        return Warrant.createBuilder()
                .title(this.title)
                .subscriptionAmount(this.subscriptionAmount)
                .standardAmount(this.standardAmount)
                .build();
    }
}
