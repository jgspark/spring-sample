package com.example.contract.dto.model.contract;

import com.example.contract.controller.request.ContractUpdateRequest;
import com.example.contract.domain.contract.ContractState;
import java.util.Set;
import lombok.Getter;

@Getter
public class ContractUpdateModel {

    private final Long id;

    private final Integer term;

    private final Set<Long> warrantIds;

    private final ContractState state;

    public ContractUpdateModel(Long id, ContractUpdateRequest req) {
        this.id = id;
        this.term = req.getTerm();
        this.warrantIds = req.getWarrantIds();
        this.state = req.getState();
    }
}
