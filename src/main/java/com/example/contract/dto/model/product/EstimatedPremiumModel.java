package com.example.contract.dto.model.product;

import java.util.Collection;
import java.util.List;
public record EstimatedPremiumModel(Long id, Collection<Long> warrantIds) {

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
