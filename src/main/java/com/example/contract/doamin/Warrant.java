package com.example.contract.doamin;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.springframework.util.Assert.notNull;

/**
 * 담보 테이블
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

    @Transient
    public BigDecimal getPremium() {
        notNull(subscriptionAmount);
        notNull(standardAmount);
        return subscriptionAmount.divide(standardAmount, 5, RoundingMode.CEILING);
    }

    @Builder(builderMethodName = "createBuilder")
    private Warrant(Long id, String title, BigDecimal subscriptionAmount, BigDecimal standardAmount) {
        this.id = id;
        this.title = title;
        this.subscriptionAmount = subscriptionAmount;
        this.standardAmount = standardAmount;
    }
}
