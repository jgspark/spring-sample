package com.example.contract.dto.model.product;

import com.example.contract.dto.request.ProductSaveRequest;
import com.example.contract.domain.entity.product.Product;
import com.example.contract.domain.entity.product.ProductTerm;
import com.example.contract.domain.entity.warrant.Warrant;
import lombok.Getter;

import java.util.Set;

@Getter
public class ProductSaveModel {

    private final String title;

    private final ProductTerm term;

    private final Set<Long> warrantIds;

    public ProductSaveModel(ProductSaveRequest req) {
        this.title = req.title();
        this.term = req.term();
        this.warrantIds = req.warrantIds();
    }

    public Product toEntity(Set<Warrant> warrants) {
        return Product.createBuilder()
                .title(title)
                .term(term)
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
