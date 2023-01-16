package com.example.contract.doamin;

import com.example.contract.config.exception.DataNotFoundException;
import com.example.contract.doamin.embeddable.ProductTerm;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.springframework.util.Assert.notNull;

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

    @PrePersist
    private void prePersist() {
        term.checkTerm();
    }

    @Transient
    public BigDecimal calculatePremium() {

        notNull(term);

        if (warrants.isEmpty()) {
            throw new DataNotFoundException("Product Id is " + this.id);
        }

        BigDecimal range = new BigDecimal(this.term.getRange());

        return range
                .multiply(warrants.stream().map(Warrant::getPremium).reduce(BigDecimal.ZERO, BigDecimal::add))
                .setScale(2, RoundingMode.FLOOR);
    }

    @Builder(builderMethodName = "createBuilder")
    private Product(String title, ProductTerm term, Set<Warrant> warrants) {
        this.title = title;
        this.term = term;
        this.warrants = Optional.ofNullable(warrants).orElse(new HashSet<>());
    }
}
