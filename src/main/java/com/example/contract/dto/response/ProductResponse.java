package com.example.contract.dto.response;

import com.example.contract.domain.entity.product.Product;
import com.example.contract.domain.entity.product.ProductTerm;
import com.example.contract.domain.entity.warrant.Warrant;

import java.util.Set;
import java.util.stream.Collectors;

public record ProductResponse(Long id, String title, ProductTerm term, Set<Long> warrantIds) {
    public ProductResponse(Product product) {
        this(product.getId(),
                product.getTitle(),
                product.getTerm(),
                product.getWarrants().stream().map(Warrant::getId).collect(Collectors.toSet()));
    }

    @Override
    public String toString() {
        return "ProductResponse{" +
                "id=" + id +
                ", title='" + title +
                ", term=" + term +
                ", warrantIds=" + warrantIds +
                '}';
    }
}
