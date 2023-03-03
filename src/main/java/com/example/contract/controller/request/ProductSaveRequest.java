package com.example.contract.controller.request;

import com.example.contract.domain.product.Product;
import com.example.contract.domain.warrant.Warrant;
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

    public Product toEntity(Set<Warrant> warrants) {
        return Product.createBuilder()
                .title(title)
                .term(term)
                .warrants(warrants)
                .build();
    }
}
