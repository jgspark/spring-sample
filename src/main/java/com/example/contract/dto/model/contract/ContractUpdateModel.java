package com.example.contract.dto.model.contract;

import com.example.contract.dto.request.ContractUpdateRequest;
import com.example.contract.domain.entity.contract.ContractState;
import lombok.Getter;

import java.util.Set;

@Getter
public class ContractUpdateModel {

    private final Long id;

    private final Integer term;

    private final Set<Long> warrantIds;

    private final ContractState state;

    public static ContractUpdateModel of(Long id, ContractUpdateRequest req) {
        return new ContractUpdateModel(id, req);
    }

    private ContractUpdateModel(Long id, ContractUpdateRequest req) {
        this.id = id;
        this.term = req.term();
        this.warrantIds = req.warrantIds();
        this.state = req.state();
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
