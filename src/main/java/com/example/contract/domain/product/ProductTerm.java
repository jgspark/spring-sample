package com.example.contract.domain.product;

import com.example.contract.exception.AppException;
import com.example.contract.domain.product.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import static org.springframework.util.Assert.notNull;

/**
 * 상품 기간
 *
 * @see Product
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

    /**
     * 상품의 기간의 구하기 위한 메소드 입니다.
     * <p>
     * 예) 시작월 그리고 끝월이 각가 1 과 3일 때 리턴 되는 값은 2가 됩니다.
     * <p>
     *
     * @return 기간
     * @throws IllegalArgumentException startMonth 와 endMonth 가 null 이면 예외
     */
    @Transient
    public Integer getRange() {

        notNull(startMonth);
        notNull(endMonth);

        return Math.subtractExact(endMonth, startMonth);
    }

    /**
     * 기간에 대한 조건을 체크하는 메소드 입니다.
     * <p>
     * 해당 기간의 경우 startMonth 가 endMonth 보다 크면 안됩니다.
     *
     * @throws IllegalArgumentException startMonth 와 endMonth 가 null 이면 예외
     * @throws AppException             해당 기간의 경우 startMonth 가 endMonth 보다 크면 예외
     */
    @Transient
    public void checkTerm() {

        notNull(startMonth);
        notNull(endMonth);

        if (isOverStartMonth()) {
            throw new AppException("StartDate is Over EndDate");
        }
    }

    /**
     * startMonth 와 endMonth 를 체크하기 위한 메소드 입니다.
     * <p>
     * 기간의 경우 startMonth 가 endMonth 보다 크면 안됩니다.
     *
     * @return 시작월이 크면 true 를 아니면 false
     */
    private boolean isOverStartMonth() {
        return startMonth > endMonth;
    }
}
