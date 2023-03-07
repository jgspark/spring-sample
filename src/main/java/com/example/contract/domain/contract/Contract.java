package com.example.contract.domain.contract;

import com.example.contract.domain.product.Product;
import com.example.contract.domain.warrant.Warrant;
import com.example.contract.exception.DataNotFoundException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * 계약 테이블
 *
 * @see com.example.contract.repository.ProductRepository
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
        checkEntity();
    }

    @Builder(builderMethodName = "createBuilder")
    private Contract(Long id, Product product, Set<Warrant> warrants, Integer term, Date startDate, Date endDate, BigDecimal premium) {
        this.id = id;
        this.product = product;
        this.warrants = Optional.ofNullable(warrants).orElseGet(HashSet::new);
        this.term = term;
        this.startDate = startDate;
        this.endDate = endDate;
        this.premium = premium;
    }

    /**
     * 상품의 기간 만료 상태를 체크하기 위한 메소드 입니다.
     *
     * @return 상태가 기간 만료 라면 true 를 아니면 false
     */
    @Transient
    public boolean isExpiration() {
        return ContractState.EXPIRATION == state;
    }

    /**
     * 상품에 update 를 시키기 위한 메소드 입니다.
     *
     * @param warrants 담보 데이터
     * @param term     계약 기간 데이터
     * @param state    상태 데이터
     * @param premium  총 보험료
     */
    @Transient
    public void update(Set<Warrant> warrants, Integer term, ContractState state, BigDecimal premium) {
        Assert.notNull(state, "state is not null");
        Assert.notNull(term, "term is not null");
        this.warrants.addAll(warrants);
        this.term = term;
        this.state = state;
        this.premium = premium;
    }

    /**
     * 계약 기간을 체크해주는 메소드 입니다.
     *
     * @return 계약 기간이 null 이거나 혹은 0 일 경우 true 로 반환 하고 아니면 false
     */
    @Transient
    private boolean isEmptyTerm() {
        return ObjectUtils.isEmpty(this.term) || this.term == 0;
    }

    /**
     * 계약 조건의 저장시 조건을 체크하는 메소드 입니다.
     *
     * @throws DataNotFoundException 계약 기간이 비어있다면 예외
     */
    @Transient
    private void checkEntity() {
        if (isEmptyTerm()) {
            throw new DataNotFoundException("empty data");
        }
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", product=" + product +
                ", warrants=" + warrants +
                ", term=" + term +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", premium=" + premium +
                ", state=" + state +
                '}';
    }
}
