package com.example.contract.web.dto;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

/**
 * todo : 담보 데이터 가공해서 내려주기
 */
public interface EstimatedPremium {

    @Value("#{target.title}")
    String getProductTitle();

    @Value("#{target.term.getRange()}")
    Integer getTerm();

    @Value("#{target.calculatePremium()}")
    BigDecimal getPremium();
}
