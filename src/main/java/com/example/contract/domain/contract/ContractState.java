package com.example.contract.domain.contract;

import com.example.contract.domain.contract.Contract;

/**
 * 계약의 상태
 *
 * @see Contract
 */
public enum ContractState {
    /**
     * 정상 계약
     */
    NORMAL,
    /**
     * 청약 철회
     */
    WITHDRAWAL,
    /**
     * 기간 만료
     */
    EXPIRATION
}
