package com.example.contract.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContractSaveRequest {

    @NotNull
    private Long productId;

    @NotNull
    private Set<Long> warrantIds;

    @NotNull
    private Integer term;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

}
