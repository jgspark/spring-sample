package com.example.contract.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.Set;

public record ContractSaveRequest(
        @NotNull
        Long productId,
        @NotEmpty
        Set<Long> warrantIds,
        @NotNull
        Integer term,
        @NotNull
        Date startDate,
        @NotNull
        Date endDate
) {
}
