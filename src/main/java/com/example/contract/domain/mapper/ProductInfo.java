package com.example.contract.domain.mapper;

import org.springframework.beans.factory.annotation.Value;

public interface ProductInfo {
    Long getId();

    String getTitle();

    @Value("#{target.term.getRange()}")
    Integer getRange();
}
