package com.example.contract.mock.product;

import com.example.contract.domain.product.Product;
import com.example.contract.dto.mapper.EstimatedPremium;

import java.math.BigDecimal;

public class EstimatedPremiumImpl implements EstimatedPremium {
    private final Product product;

    public EstimatedPremiumImpl(Product product) {
        this.product = product;
    }

    @Override
    public String getProductTitle() {
        return product.getTitle();
    }

    @Override
    public Integer getTerm() {
        return product.getTerm().getRange();
    }

    @Override
    public BigDecimal getPremium() {
        return product.calculatePremium();
    }
}
