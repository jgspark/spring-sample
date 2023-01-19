package com.example.contract.web.dto;

import com.example.contract.doamin.Product;
import com.example.contract.doamin.Warrant;
import com.example.contract.doamin.embeddable.ProductTerm;
import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class ProductResponse {

    private final Long id;

    private final String title;

    private final ProductTerm term;

    private final Set<Long> warrantIds;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.term = product.getTerm();
        this.warrantIds = product.getWarrants().stream().map(Warrant::getId).collect(Collectors.toSet());
    }
}
