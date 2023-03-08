package com.example.contract.domain.mapper;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public interface EstimatedPremium {

    @Value("#{target.title}")
    String getProductTitle();

    @Value("#{target.term.getRange()}")
    Integer getTerm();

    @Value("#{target.calculatePremium()}")
    BigDecimal getPremium();
}
