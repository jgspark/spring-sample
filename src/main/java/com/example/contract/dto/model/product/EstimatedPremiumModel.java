package com.example.contract.dto.model.product;

import lombok.Getter;

import java.util.Collection;

@Getter
public class EstimatedPremiumModel {

    private Long id;

    private Collection<Long> warrantIds;

    public EstimatedPremiumModel(Long id, Collection<Long> warrantIds) {
        this.id = id;
        this.warrantIds = warrantIds;
    }
}
