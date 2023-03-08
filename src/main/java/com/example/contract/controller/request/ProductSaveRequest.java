package com.example.contract.controller.request;

import com.example.contract.domain.entity.product.ProductTerm;
import lombok.Getter;

import jakarta.validation.constraints.NotNull;
import java.util.Set;

@Getter
public class ProductSaveRequest {

    @NotNull
    private String title;

    @NotNull
    private ProductTerm term;

    @NotNull
    private Set<Long> warrantIds;

}
