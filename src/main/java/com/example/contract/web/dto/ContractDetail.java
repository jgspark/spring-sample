package com.example.contract.web.dto;

import com.example.contract.enums.ContractState;

import java.math.BigDecimal;
import java.util.Date;

public interface ContractDetail {

    Long getId();

    Integer getTerm();

    Date getStartDate();

    Date getEndDate();

    BigDecimal getPremium();

    ContractState getState();
}
