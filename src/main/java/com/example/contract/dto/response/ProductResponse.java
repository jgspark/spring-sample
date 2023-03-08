package com.example.contract.dto.response;

import com.example.contract.domain.entity.product.Product;
import com.example.contract.domain.entity.product.ProductTerm;
import com.example.contract.domain.entity.warrant.Warrant;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;

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

    @Override
    public String toString() {
        return "ProductResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", term=" + term +
                ", warrantIds=" + warrantIds +
                '}';
    }
}
