package com.example.contract.dto.model.contract;

import com.example.contract.domain.entity.contract.ContractState;
import com.example.contract.dto.request.ContractUpdateRequest;

import java.util.Set;

public record ContractUpdateModel(Long id, Integer term, Set<Long> warrantIds, ContractState state) {

    public static ContractUpdateModel of(Long id, ContractUpdateRequest req) {
        return new ContractUpdateModel(id, req.term(), req.warrantIds(), req.state());
    }

    @Override
    public String toString() {
        return "ContractUpdateModel{" +
                "id=" + id +
                ", term=" + term +
                ", warrantIds=" + warrantIds +
                ", state=" + state +
                '}';
    }
}
