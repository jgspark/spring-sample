package com.example.contract.data.projections;

import java.math.BigDecimal;

public interface WarrantInfo {
    Long getId();

    String getTitle();

    BigDecimal getSubscriptionAmount();

    BigDecimal getStandardAmount();
}
