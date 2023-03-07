package com.example.contract.domain.warrant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import static org.springframework.util.Assert.notNull;

/**
 * 담보
 */
@Getter
@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Warrant {

    @Id
    @Comment("담보 아이디")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("담보명")
    @Column(nullable = false)
    private String title;

    @Comment("가입 금액")
    @Column(nullable = false)
    private BigDecimal subscriptionAmount;

    @Comment("기준 금액")
    @Column(nullable = false)
    private BigDecimal standardAmount;

    /**
     * 총 보험료 로직에 사용되는 계산 값 메소드 입니다.
     * <p>
     * 나누기를 할 때 bigDecimal 를 사용을 하였는데 scale 를 설정을 안하면 예외가 발생이 되어서 추가하였습니다.
     * <p>
     * 요건상 2자리 까지 소수점을 허용을 하지만 해당 조건은 총 보험료를 구한 메소드에 작성을 하였습니다.
     * <p>
     * 해당 5자리를 설정한 경우 정확한 계산값을 구하기 위해서 5자리로 설정을 하였습니다.
     *
     * @return 가입 급액 / 기준 금액
     * @throws IllegalArgumentException 가입 금액 과 기준 금액이 null 이면 예외
     */
    @Transient
    public BigDecimal getPremium() {
        notNull(subscriptionAmount , "subscriptionAmount is not null");
        notNull(standardAmount , "standardAmount is not null");
        return subscriptionAmount.divide(standardAmount, 5, RoundingMode.CEILING);
    }

    @Builder(builderMethodName = "createBuilder")
    private Warrant(Long id, String title, BigDecimal subscriptionAmount, BigDecimal standardAmount) {
        this.id = id;
        this.title = title;
        this.subscriptionAmount = subscriptionAmount;
        this.standardAmount = standardAmount;
    }

    @Override
    public String toString() {
        return "Warrant{" +
                "id=" + id + '\'' +
                ", title='" + title + '\'' +
                ", subscriptionAmount=" + subscriptionAmount +  '\'' +
                ", standardAmount=" + standardAmount + '\'' +
                '}';
    }
}
