package com.example.contract.dto.model.contract;

import com.example.contract.controller.request.ContractSaveRequest;
import com.example.contract.domain.contract.Contract;
import com.example.contract.domain.product.Product;
import lombok.Getter;

import javax.validation.constraints.NotNull;
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
        this.productId = req.getProductId();
        this.warrantIds = req.getWarrantIds();
        this.term = req.getTerm();
        this.startDate = req.getStartDate();
        this.endDate = req.getEndDate();
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
}
