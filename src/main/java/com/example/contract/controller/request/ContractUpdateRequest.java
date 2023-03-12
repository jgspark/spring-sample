package com.example.contract.controller.request;

import com.example.contract.domain.entity.contract.ContractState;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
public record ContractUpdateRequest(
        @NotNull
        Integer term,
        @NotEmpty
        Set<Long> warrantIds,
        @NotNull
        ContractState state
) {
}
