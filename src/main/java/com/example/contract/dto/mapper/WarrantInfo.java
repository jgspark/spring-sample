package com.example.contract.dto.mapper;

import java.math.BigDecimal;

public interface WarrantInfo {
    Long getId();

    String getTitle();

    BigDecimal getSubscriptionAmount();

    BigDecimal getStandardAmount();
}
