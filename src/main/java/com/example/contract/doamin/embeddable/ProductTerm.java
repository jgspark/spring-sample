package com.example.contract.doamin.embeddable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import static org.springframework.util.Assert.notNull;

/**
 * todo : startMonth and endMonth 비교 로직 추가
 */
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductTerm {

    @Column(nullable = false)
    private Integer startMonth;

    @Column(nullable = false)
    private Integer endMonth;

    @Transient
    public Integer getRange() {

        notNull(startMonth);
        notNull(endMonth);

        return Math.subtractExact(endMonth, startMonth);
    }

    @Transient
    public void checkTerm() {

        notNull(startMonth);
        notNull(endMonth);

        //todo : custom exception 처리
        if (isOverStartMonth()) {
            throw new RuntimeException("startdate is small endMonth");
        }
    }

    private boolean isOverStartMonth() {
        if (startMonth > endMonth) {
            return true;
        }
        return false;
    }
}
