package com.example.contract.web.dto;

import com.example.contract.doamin.Product;
import com.example.contract.doamin.Warrant;
import com.example.contract.doamin.embeddable.ProductTerm;
import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class ProductResponse {

    private Long id;

    private String title;

    private ProductTerm term;

    private Set<Long> warrantIds;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.term = product.getTerm();
        this.warrantIds = product.getWarrants().stream().map(Warrant::getId).collect(Collectors.toSet());
    }
}
