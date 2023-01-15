package com.example.contract.web.dto;

import com.example.contract.doamin.Contract;
import com.example.contract.doamin.Warrant;
import com.example.contract.enums.ContractState;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class ContractResponse {

    private Long id;

    private Long productId;

    private Set<Long> warrantIds;

    private Integer term;

    private Date startDate;

    private Date endDate;

    private BigDecimal premium;

    private ContractState state;

    public ContractResponse(Contract contract) {
        this.id = contract.getId();
        this.productId = contract.getProduct().getId();
        this.warrantIds = contract.getWarrants().stream().map(Warrant::getId).collect(Collectors.toSet());
        this.term = contract.getTerm();
        this.startDate = contract.getStartDate();
        this.endDate = contract.getEndDate();
        this.premium = contract.getPremium();
        this.state = contract.getState();
    }
}
