package com.example.contract.dto.model.product;

import com.example.contract.controller.request.ProductSaveRequest;
import com.example.contract.domain.product.Product;
import com.example.contract.domain.product.ProductTerm;
import com.example.contract.domain.warrant.Warrant;
import java.util.Set;
import lombok.Getter;

@Getter
public class ProductSaveModel {

    private final String title;

    private final ProductTerm term;

    private final Set<Long> warrantIds;

    public ProductSaveModel(ProductSaveRequest req) {
        this.title = req.getTitle();
        this.term = req.getTerm();
        this.warrantIds = req.getWarrantIds();
    }

    public Product toEntity(Set<Warrant> warrants) {
        return Product.createBuilder()
                .title(title)
                .term(term)
                .warrants(warrants)
                .build();
    }
}
