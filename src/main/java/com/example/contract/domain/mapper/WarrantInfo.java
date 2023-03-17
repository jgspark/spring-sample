package com.example.contract.domain.mapper;

import java.math.BigDecimal;

public interface WarrantInfo {
    Long getId();

    String getTitle();

    BigDecimal getSubscriptionAmount();

    BigDecimal getStandardAmount();
}

