package com.example.contract.web.dto;

import java.math.BigDecimal;

public interface WarrantInfo {
    Long getId();

    String getTitle();

    BigDecimal getSubscriptionAmount();

    BigDecimal getStandardAmount();
}
