package com.example.contract.web.dto;

import com.example.contract.doamin.Warrant;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class WarrantSaveRequest {

    private String title;

    private BigDecimal subscriptionAmount;

    private BigDecimal standardAmount;

    public Warrant toEntity() {
        return Warrant.createBuilder()
                .title(this.title)
                .subscriptionAmount(this.subscriptionAmount)
                .standardAmount(this.standardAmount)
                .build();
    }
}
