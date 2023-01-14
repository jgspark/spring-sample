package com.example.contract.doamin;

import com.example.contract.doamin.embeddable.ProductTerm;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * 상품 테이블
 */
@Getter
@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("상품 아이디")
    private Long id;

    @Comment("상품 명")
    @Column(nullable = false)
    private String title;

    @Comment("계약 기간 (M)")
    private ProductTerm term;

    @OneToMany
    private Set<Warrant> warrants = new HashSet<>();

    @Transient
    public BigDecimal calculatePremium() {

        Integer range = this.term.getRange();

        return new BigDecimal(range).multiply(warrants.stream()
                .map(Warrant::getPremium)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }
}
