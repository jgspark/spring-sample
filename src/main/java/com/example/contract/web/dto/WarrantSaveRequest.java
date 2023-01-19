package com.example.contract.web.dto;

import com.example.contract.doamin.Warrant;
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

    public Warrant toEntity() {
        return Warrant.createBuilder()
                .title(this.title)
                .subscriptionAmount(this.subscriptionAmount)
                .standardAmount(this.standardAmount)
                .build();
    }
}
