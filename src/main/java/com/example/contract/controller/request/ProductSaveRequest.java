package com.example.contract.controller.request;

import com.example.contract.domain.entity.product.ProductTerm;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record ProductSaveRequest(
        @NotNull
        String title,
        @NotNull
        ProductTerm term,
        @NotEmpty
        Set<Long> warrantIds
) {
}
