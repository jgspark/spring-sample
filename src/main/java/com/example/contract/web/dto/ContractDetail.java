package com.example.contract.web.dto;

import com.example.contract.enums.ContractState;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

public interface ContractDetail {

    Long getId();

    Integer getTerm();

    Date getStartDate();

    Date getEndDate();

    BigDecimal getPremium();

    ContractState getState();

    @Value("#{target.product}")
    ProductInfo getProduct();

    @Value("#{target.warrants}")
    Set<WarrantInfo> getWarrants();

    interface ProductInfo {

        Long getId();

        String getTitle();

        @Value("#{target.term.getRange()}")
        Integer getRange();
    }

    interface WarrantInfo {

        Long getId();

        String getTitle();

        BigDecimal getSubscriptionAmount();

        BigDecimal getStandardAmount();
    }
}
