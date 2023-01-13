package com.example.contract.doamin;

import com.example.contract.enums.ContractState;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * todo : 담보 와 상품 매핑은 아직 하지 않은 상태
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

    @Comment("상품 번호")
    private Long productId;

    @Comment("가입 담보")
    private Long guaranteeId;

    @Comment("계약 기간")
    private String term;

    @Comment("보험 시작일")
    private String startAt;

    @Comment("보험 종료일")
    private String endAt;

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
    private Contract(Long productId, Long guaranteeId, String term, String startAt, String endAt, BigDecimal premium, ContractState state) {
        this.productId = productId;
        this.guaranteeId = guaranteeId;
        this.term = term;
        this.startAt = startAt;
        this.endAt = endAt;
        this.premium = premium;
        this.state = state;
    }
}
