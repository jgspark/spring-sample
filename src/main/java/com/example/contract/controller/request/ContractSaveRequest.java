package com.example.contract.controller.request;

import com.example.contract.domain.contract.Contract;
import com.example.contract.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContractSaveRequest {

    @NotNull
    private Long productId;

    @NotNull
    private Set<Long> warrantIds;

    @NotNull
    private Integer term;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

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
