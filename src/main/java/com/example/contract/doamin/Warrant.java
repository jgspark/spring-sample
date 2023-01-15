package com.example.contract.doamin;

import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.math.BigDecimal;

import static org.springframework.util.Assert.notNull;

/**
 * 담보 테이블
 */
@Getter
@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
        return subscriptionAmount.divide(standardAmount);
    }

    @Builder(builderMethodName = "createBuilder")
    private Warrant(String title, BigDecimal subscriptionAmount, BigDecimal standardAmount) {
        this.title = title;
        this.subscriptionAmount = subscriptionAmount;
        this.standardAmount = standardAmount;
    }
}
