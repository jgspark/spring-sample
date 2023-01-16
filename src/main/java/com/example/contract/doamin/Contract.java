package com.example.contract.doamin;

import com.example.contract.enums.ContractState;
import com.example.contract.utils.CalculateUtil;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * 계약 테이블
 */
@Getter
@Entity
@Table
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("계약 번호")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @Comment("상품 번호")
    private Product product;

    @ManyToMany
    @Comment("가입 담보")
    private Set<Warrant> warrants = new HashSet<>();

    @Comment("계약 기간 (M)")
    @Column(nullable = false)
    private Integer term;

    @Comment("보험 시작일")
    @Column(nullable = false)
    private Date startDate;

    @Comment("보험 종료일")
    @Column(nullable = false)
    private Date endDate;

    @Comment("총 보험료")
    @Column(nullable = false)
    private BigDecimal premium;

    @Enumerated(EnumType.STRING)
    @Comment("계약 상태")
    @Column(nullable = false)
    private ContractState state;

    @PrePersist
    private void prePersist() {
        this.state = ContractState.NORMAL;
        CalculateUtil.checkedEmpty(this.term);
    }

    @Builder(builderMethodName = "createBuilder")
    private Contract(Product product, Set<Warrant> warrants, Integer term, Date startDate, Date endDate, BigDecimal premium) {
        this.product = product;
        this.warrants = Optional.ofNullable(warrants).orElseGet(HashSet::new);
        this.term = term;
        this.startDate = startDate;
        this.endDate = endDate;
        this.premium = premium;
    }

    @Transient
    public boolean isExpiration() {
        return ContractState.EXPIRATION == state;
    }

    public void update(Set<Warrant> warrants, Integer term, ContractState state) {
        this.warrants.addAll(warrants);
        this.term = term;
        this.state = state;
    }
}
