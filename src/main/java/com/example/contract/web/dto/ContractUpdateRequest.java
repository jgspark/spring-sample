package com.example.contract.web.dto;

import com.example.contract.enums.ContractState;
import lombok.Getter;

import java.util.Set;

@Getter
public class ContractUpdateRequest {

    private Set<Long> warrantIds;

    private Integer term;

    private ContractState state;
}
