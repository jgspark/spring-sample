package com.example.contract.doamin;

import com.example.contract.enums.ContractState;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 계약 테이블
 */
@Getter
@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("계약 번호")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @Comment("상품 번호")
    private Product product;

    @OneToMany
    @Comment("가입 담보")
    private Set<Warrant> warrants = new HashSet<>();

    @Comment("계약 기간 (M)")
    private Integer term;

    @Comment("보험 시작일")
    private Date startDate;

    @Comment("보험 종료일")
    private Date endDate;

    @Comment("총 보험료")
    private BigDecimal premium;

    @Enumerated(EnumType.STRING)
    @Comment("계약 상태")
    private ContractState state;

    @PrePersist
    void prePersist() {
        this.state = ContractState.NORMAL;
    }

    @Builder(builderMethodName = "createBuilder")
    private Contract(Product product, Set<Warrant> warrants, Integer term, Date startDate, Date endDate, BigDecimal premium) {
        this.product = product;
        this.warrants = warrants;
        this.term = term;
        this.startDate = startDate;
        this.endDate = endDate;
        this.premium = premium;
    }
}
