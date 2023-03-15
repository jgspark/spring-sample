package com.example.contract.dto.response;

import com.example.contract.domain.entity.warrant.Warrant;

import java.math.BigDecimal;

public record WarrantResponse(Long id, String title, BigDecimal subscriptionAmount, BigDecimal standardAmount) {

    public static WarrantResponse of(Warrant warrant) {
        return new WarrantResponse(warrant);
    }
    
    private WarrantResponse(Warrant warrant) {
        this(warrant.getId(), warrant.getTitle(), warrant.getSubscriptionAmount(), warrant.getStandardAmount());
    }

    @Override
    public String toString() {
        return "WarrantResponse{" +
                "id=" + id +
                ", title='" + title +
                ", subscriptionAmount=" + subscriptionAmount +
                ", standardAmount=" + standardAmount +
                '}';
    }
}
