package com.example.contract.dto.response;

import com.example.contract.domain.mapper.ProductInfo;

public record ProductInfoRequest(
        Long id,
        String title,
        Integer range
) {

    public static ProductInfoRequest of(ProductInfo info) {
        return new ProductInfoRequest(info.getId(), info.getTitle(), info.getRange());
    }
}
