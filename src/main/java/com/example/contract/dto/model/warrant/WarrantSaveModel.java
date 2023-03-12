package com.example.contract.dto.model.warrant;

import com.example.contract.controller.request.WarrantSaveRequest;
import com.example.contract.domain.entity.warrant.Warrant;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class WarrantSaveModel {

    private final String title;

    private final BigDecimal subscriptionAmount;

    private final BigDecimal standardAmount;

    public WarrantSaveModel(WarrantSaveRequest req) {
        this.title = req.title();
        this.subscriptionAmount = req.subscriptionAmount();
        this.standardAmount = req.standardAmount();
    }

    public Warrant toEntity() {
        return Warrant.createBuilder()
                .title(this.title)
                .subscriptionAmount(this.subscriptionAmount)
                .standardAmount(this.standardAmount)
                .build();
    }

    @Override
    public String toString() {
        return "WarrantSaveModel{" +
                "title='" + title + '\'' +
                ", subscriptionAmount=" + subscriptionAmount +
                ", standardAmount=" + standardAmount +
                '}';
    }
}
