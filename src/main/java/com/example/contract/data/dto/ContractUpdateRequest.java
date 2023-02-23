package com.example.contract.data.dto;

import com.example.contract.data.enums.ContractState;
import lombok.Getter;

import javax.validation.constraints.NotNull;
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
