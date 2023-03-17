package com.example.contract.dto.model.product;

import com.example.contract.domain.entity.product.Product;
import com.example.contract.domain.entity.product.ProductTerm;
import com.example.contract.domain.entity.warrant.Warrant;
import com.example.contract.dto.request.ProductSaveRequest;

import java.util.Set;

public record ProductSaveModel(String title, ProductTerm term, Set<Long> warrantIds) {

    public static ProductSaveModel of(ProductSaveRequest req) {
        return new ProductSaveModel(req.title(), req.term(), req.warrantIds());
    }

    public Product toEntity(Set<Warrant> warrants) {
        return Product.createBuilder()
                .title(title())
                .term(term())
                .warrants(warrants)
                .build();
    }

    @Override
    public String toString() {
        return "ProductSaveModel{" +
                "title='" + title + '\'' +
                ", term=" + term +
                ", warrantIds=" + warrantIds +
                '}';
    }
}
