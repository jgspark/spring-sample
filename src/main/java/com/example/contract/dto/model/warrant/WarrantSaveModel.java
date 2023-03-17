package com.example.contract.dto.model.warrant;

import com.example.contract.domain.entity.warrant.Warrant;
import com.example.contract.dto.request.WarrantSaveRequest;

import java.math.BigDecimal;

public record WarrantSaveModel(String title, BigDecimal subscriptionAmount, BigDecimal standardAmount) {

    public static WarrantSaveModel of(WarrantSaveRequest req) {
        return new WarrantSaveModel(req.title(), req.subscriptionAmount(), req.standardAmount());
    }

    public Warrant toEntity() {
        return Warrant.createBuilder()
                .title(title)
                .subscriptionAmount(subscriptionAmount)
                .standardAmount(standardAmount)
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

