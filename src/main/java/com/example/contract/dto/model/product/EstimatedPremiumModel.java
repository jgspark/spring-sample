package com.example.contract.dto.model.product;

import lombok.Getter;

import java.util.Collection;
import java.util.List;

@Getter
public class EstimatedPremiumModel {

    private final Long id;

    private final Collection<Long> warrantIds;

    private EstimatedPremiumModel(Long id, Collection<Long> warrantIds) {
        this.id = id;
        this.warrantIds = warrantIds;
    }

    public static EstimatedPremiumModel of(Long id, List<Long> warrantIds) {
        return new EstimatedPremiumModel(id, warrantIds);
    }

    @Override
    public String toString() {
        return "EstimatedPremiumModel{" +
                "id=" + id +
                ", warrantIds=" + warrantIds +
                '}';
    }
}
