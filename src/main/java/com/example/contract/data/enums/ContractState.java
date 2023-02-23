package com.example.contract.data.enums;

import com.example.contract.data.doamin.Contract;

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
