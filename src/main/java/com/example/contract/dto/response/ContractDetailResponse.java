package com.example.contract.dto.response;

import com.example.contract.domain.entity.contract.ContractState;
import com.example.contract.domain.mapper.ContractDetail;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public record ContractDetailResponse(
        Long id,
        Integer term,
        Date startDate,
        Date endDate,
        BigDecimal premium,
        ContractState state,
        ProductInfoRequest product,
        Set<WarrantInfoRequest> warrants
) {

    public static ContractDetailResponse of(ContractDetail detail) {
        return new ContractDetailResponse(
                detail.getId(),
                detail.getTerm(),
                detail.getStartDate(),
                detail.getEndDate(),
                detail.getPremium(),
                detail.getState(),
                ProductInfoRequest.of(detail.getProduct()),
                detail.getWarrants().stream().map(WarrantInfoRequest::of).collect(Collectors.toSet())
        );
    }
}
