package com.example.contract.doamin.embeddable;

import com.example.contract.config.exception.AppException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import static org.springframework.util.Assert.notNull;

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

        if (isOverStartMonth()) {
            throw new AppException("StartDate is Over EndDate");
        }
    }

    private boolean isOverStartMonth() {
        if (startMonth > endMonth) {
            return true;
        }
        return false;
    }
}
