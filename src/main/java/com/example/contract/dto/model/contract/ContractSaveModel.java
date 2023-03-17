package com.example.contract.dto.model.contract;

import com.example.contract.domain.entity.contract.Contract;
import com.example.contract.domain.entity.product.Product;
import com.example.contract.dto.request.ContractSaveRequest;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

public record ContractSaveModel(Long productId, Set<Long> warrantIds, Integer term, Date startDate, Date endDate) {

    public static ContractSaveModel of(ContractSaveRequest req) {
        return new ContractSaveModel(req.productId(), req.warrantIds(), req.term(), req.startDate(), req.endDate());
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
