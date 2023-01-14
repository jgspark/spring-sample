package com.example.contract.web.dto;

import com.example.contract.doamin.Contract;
import com.example.contract.doamin.Product;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@Getter
public class ContractSaveRequest {

    private Long productId;

    private Set<Long> warrantIds;

    public Contract toEntity(@NotNull Product product) {
        return Contract.createBuilder()
                .product(product)
                .build();
    }
}
