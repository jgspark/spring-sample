package com.example.contract.data.dto;

import com.example.contract.data.doamin.Product;
import com.example.contract.data.doamin.Warrant;
import com.example.contract.data.doamin.embeddable.ProductTerm;
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
