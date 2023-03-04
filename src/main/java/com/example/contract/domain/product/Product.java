package com.example.contract.domain.product;

import com.example.contract.domain.warrant.Warrant;
import com.example.contract.exception.DataNotFoundException;
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

    @ManyToMany
    private Set<Warrant> warrants = new HashSet<>();

    @PrePersist
    private void prePersist() {
        term.checkTerm();
    }

    /**
     * 총 보험료를 계산을 하는 메소드 입니다.
     * <p>
     * 소숫점 2자리에서 절사가 됩니다.
     *
     * @return 총 보험료
     * @throws DataNotFoundException    담보 데이터가 매핑이 되어 있지 않거나 혹은 없다면 예외
     * @throws IllegalArgumentException 계약 기간이 null 이면 예외
     */
    @Transient
    public BigDecimal calculatePremium() {

        notNull(term);

        if (warrants.isEmpty()) {
            throw new DataNotFoundException("Product Id is " + this.id);
        }

        BigDecimal range = new BigDecimal(this.term.getRange());

        return range.multiply(warrants.stream().map(Warrant::getPremium).reduce(BigDecimal.ZERO, BigDecimal::add)).setScale(2,
                                                                                                                            RoundingMode.FLOOR);
    }

    @Builder(builderMethodName = "createBuilder")
    private Product(Long id, String title, ProductTerm term, Set<Warrant> warrants) {
        this.id = id;
        this.title = title;
        this.term = term;
        this.warrants = Optional.ofNullable(warrants).orElse(new HashSet<>());
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id + '\'' +
                ", title='" + title + '\'' +
                ", term=" + term + '\'' +
                ", warrants=" + warrants + '\'' +
                '}';
    }
}
