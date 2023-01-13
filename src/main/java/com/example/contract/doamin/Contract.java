package com.example.contract.doamin;

import com.example.contract.enums.ContractState;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.math.BigDecimal;

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

}
