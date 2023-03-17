package com.example.contract.dto.response;

import com.example.contract.domain.mapper.EstimatedPremium;

import java.math.BigDecimal;

public record EstimatedPremiumResponse(
        String title,
        Integer term,
        BigDecimal premium
) {

    public static EstimatedPremiumResponse of(EstimatedPremium premium) {
        return new EstimatedPremiumResponse(premium.getProductTitle(), premium.getTerm(), premium.getPremium());
    }
}
