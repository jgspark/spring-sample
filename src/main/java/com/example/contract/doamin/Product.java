package com.example.contract.doamin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;

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
    @Column(nullable = false)
    private Integer term;

    @PrePersist
    private void prePersist() {

        if (isEmptyTerm()) {
            throw new RuntimeException("term is bigger zero and is not null");
        }
    }


    @Transient
    private boolean isEmptyTerm() {
        return ObjectUtils.isEmpty(term) || 0 == term;
    }
}
