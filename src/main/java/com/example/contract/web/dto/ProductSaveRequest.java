package com.example.contract.web.dto;

import com.example.contract.doamin.Product;
import com.example.contract.doamin.Warrant;
import com.example.contract.doamin.embeddable.ProductTerm;
import lombok.Getter;

import java.util.Collection;
import java.util.Set;

@Getter
public class ProductSaveRequest {

    private String title;

    private ProductTerm term;

    private Set<Long> warrantIds;

    public Product toEntity(Set<Warrant> warrants) {
        return Product.createBuilder()
                .title(title)
                .term(term)
                .warrants(warrants)
                .build();
    }
}
