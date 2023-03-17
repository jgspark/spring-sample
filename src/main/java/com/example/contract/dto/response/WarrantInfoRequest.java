package com.example.contract.dto.response;

import com.example.contract.domain.mapper.WarrantInfo;

import java.math.BigDecimal;

public record WarrantInfoRequest(
        Long id,
        String title,
        BigDecimal subscriptionAmount,
        BigDecimal standardAmount) {

    public static WarrantInfoRequest of(WarrantInfo info) {
        return new WarrantInfoRequest(info.getId(), info.getTitle(), info.getSubscriptionAmount(), info.getStandardAmount());
    }
}
