package com.example.contract.enums;

/**
 * 계약의 상태
 *
 * @see com.example.contract.doamin.Contract
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
