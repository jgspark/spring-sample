package com.example.contract.dto.model.contract;

import com.example.contract.controller.request.ContractSaveRequest;
import com.example.contract.domain.entity.contract.Contract;
import com.example.contract.domain.entity.product.Product;
import lombok.Getter;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Getter
public class ContractSaveModel {

    private final Long productId;

    private final Set<Long> warrantIds;

    private final Integer term;

    private final Date startDate;

    private final Date endDate;

    public ContractSaveModel(ContractSaveRequest req) {
        this.productId = req.productId();
        this.warrantIds = req.warrantIds();
        this.term = req.term();
        this.startDate = req.startDate();
        this.endDate = req.endDate();
    }

    public Contract toEntity(@NotNull Product product, BigDecimal premium) {
        return Contract.createBuilder()
                .product(product)
                .premium(premium)
                .term(this.term)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }

    @Override
    public String toString() {
        return "ContractSaveModel{" +
                "productId=" + productId +
                ", warrantIds=" + warrantIds +
                ", term=" + term +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
