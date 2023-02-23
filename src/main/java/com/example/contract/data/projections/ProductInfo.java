package com.example.contract.data.projections;

import org.springframework.beans.factory.annotation.Value;

public interface ProductInfo {
    Long getId();

    String getTitle();

    @Value("#{target.term.getRange()}")
    Integer getRange();
}
