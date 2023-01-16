package com.example.contract.web.dto;

import com.example.contract.doamin.Contract;
import com.example.contract.doamin.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * todo : null 체크 추가
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContractSaveRequest {

    private Long productId;

    private Set<Long> warrantIds;

    private Integer term;

    private Date startDate;

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
