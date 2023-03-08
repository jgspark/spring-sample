package com.example.contract.dto.response;

import com.example.contract.domain.entity.warrant.Warrant;
import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class WarrantResponse {

    private final Long id;

    private final String title;

    private final BigDecimal subscriptionAmount;

    private final BigDecimal standardAmount;

    public WarrantResponse(Warrant warrant) {
        this.id = warrant.getId();
        this.title = warrant.getTitle();
        this.subscriptionAmount = warrant.getSubscriptionAmount();
        this.standardAmount = warrant.getStandardAmount();
    }

    @Override
    public String toString() {
        return "WarrantResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", subscriptionAmount=" + subscriptionAmount +
                ", standardAmount=" + standardAmount +
                '}';
    }
}
