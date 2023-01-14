package com.example.contract.web.dto;

import com.example.contract.doamin.Contract;
import com.example.contract.doamin.Product;
import com.example.contract.doamin.Warrant;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Getter
public class ContractSaveRequest {

    private Long productId;

    private Set<Long> warrantIds;

    private Integer term;

    private Date startDate;

    private Date endDate;

    public Contract toEntity(@NotNull Product product, @NotNull Set<Warrant> warrants, BigDecimal premium) {
        return Contract.createBuilder()
                .product(product)
                .warrants(warrants)
                .premium(premium)
                .term(this.term)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }
}
