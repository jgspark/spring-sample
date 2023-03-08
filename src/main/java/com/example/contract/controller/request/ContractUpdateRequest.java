package com.example.contract.controller.request;

import com.example.contract.domain.entity.contract.ContractState;
import lombok.Getter;

import jakarta.validation.constraints.NotNull;
import java.util.Set;

@Getter
public class ContractUpdateRequest {

    @NotNull
    private Integer term;

    @NotNull
    private Set<Long> warrantIds;

    @NotNull
    private ContractState state;
}
