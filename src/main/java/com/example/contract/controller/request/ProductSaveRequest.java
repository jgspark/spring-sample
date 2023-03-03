package com.example.contract.controller.request;

import com.example.contract.domain.product.ProductTerm;
import lombok.Getter;

import javax.validation.constraints.NotNull;
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
