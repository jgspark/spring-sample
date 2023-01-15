package com.example.contract.doamin;

import com.example.contract.doamin.embeddable.ProductTerm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    @DisplayName("총 보험료 계산 테스트 케이스 ok case")
    public void calculatePremium_ok() {

        Product mock = Product.createBuilder().title("여행자 보험").term(new ProductTerm(1, 3)).warrants(getWarrants()).build();

        BigDecimal n = mock.calculatePremium();

        BigDecimal m = BigDecimal.valueOf(20000);

        assertTrue(m.compareTo(n) == 0);
    }

    @Test
    @DisplayName("총 보험료 계산 term 이 null ")
    public void calculatePremium_fail1() {

        Product mock = Product.createBuilder().title("여행자 보험").warrants(getWarrants()).build();

        assertThrows(IllegalArgumentException.class, mock::calculatePremium);

    }

    @Test
    @DisplayName("총 보험료 계산 테스트 담보 데이터가 null")
    public void calculatePremium_fail2() {

        Product mock = Product.createBuilder().title("여행자 보험").term(new ProductTerm(1, 3)).build();

        assertThrows(RuntimeException.class, mock::calculatePremium);
    }


    private Set<Warrant> getWarrants() {
        Set<Warrant> mock = new HashSet<>();

        Warrant warrant = Warrant.createBuilder().title("상해치료").subscriptionAmount(new BigDecimal(1000000)).standardAmount(new BigDecimal(100)).build();

        mock.add(warrant);

        return mock;
    }
}
