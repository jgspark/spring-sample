package com.example.contract.dto.response;

import com.example.contract.domain.contract.Contract;
import com.example.contract.domain.contract.ContractState;
import com.example.contract.domain.warrant.Warrant;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class ContractResponse {

    private final Long id;

    private final Long productId;

    private final Set<Long> warrantIds;

    private final Integer term;

    private final Date startDate;

    private final Date endDate;

    private final BigDecimal premium;

    private final ContractState state;

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
