package com.example.contract.doamin.embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * todo : startMonth and endMonth 비교 로직 추가
 */
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductTerm {

    @Column(nullable = false)
    private Integer startMonth;

    @Column(nullable = false)
    private Integer endMonth;

    public Integer getRange() {
        return Math.subtractExact(endMonth, startMonth);
    }
}
