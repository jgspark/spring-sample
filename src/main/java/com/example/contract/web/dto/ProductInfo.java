package com.example.contract.web.dto;

import org.springframework.beans.factory.annotation.Value;

public interface ProductInfo {
    Long getId();

    String getTitle();

    @Value("#{target.term.getRange()}")
    Integer getRange();
}
