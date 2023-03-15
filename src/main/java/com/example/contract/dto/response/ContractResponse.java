package com.example.contract.dto.response;

import com.example.contract.domain.entity.contract.Contract;
import com.example.contract.domain.entity.contract.ContractState;
import com.example.contract.domain.entity.warrant.Warrant;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public record ContractResponse(Long id,
                               Long productId,
                               Set<Long> warrantIds,
                               Integer term,
                               Date startDate,
                               Date endDate,
                               BigDecimal premium,
                               ContractState state) {

    private ContractResponse(Contract contract) {
        this(contract.getId(),
                contract.getProduct().getId(),
                contract.getWarrants().stream().map(Warrant::getId).collect(Collectors.toSet()),
                contract.getTerm(),
                contract.getStartDate(),
                contract.getEndDate(),
                contract.getPremium(),
                contract.getState());
    }

    public static ContractResponse of(Contract contract) {
        return new ContractResponse(contract);
    }

    @Override
    public String toString() {
        return "ContractResponse{" +
                "id=" + id +
                ", productId=" + productId +
                ", warrantIds=" + warrantIds +
                ", term=" + term +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", premium=" + premium +
                ", state=" + state +
                '}';
    }
}
