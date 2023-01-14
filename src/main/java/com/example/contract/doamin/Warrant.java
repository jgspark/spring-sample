package com.example.contract.doamin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.math.BigDecimal;

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

    public BigDecimal getPremium() {
        return subscriptionAmount.divide(standardAmount);
    }
}
