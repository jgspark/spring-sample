package com.example.contract.doamin;

import com.example.contract.data.doamin.Product;
import com.example.contract.data.doamin.Warrant;
import com.example.contract.data.doamin.embeddable.ProductTerm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("상품 테이블 에서")
class ProductTest {

    @Nested
    @DisplayName("총 보험료 계산 로직 은")
    class CalculatePremiumMethod {

        @Test
        @DisplayName("성공을 하게 된다.")
        public void calculatePremium_ok() {

            Product mock = Product.createBuilder().title("여행자 보험").term(new ProductTerm(1, 3)).warrants(getWarrants()).build();

            BigDecimal n = mock.calculatePremium();

            BigDecimal m = BigDecimal.valueOf(20000);

            assertEquals(0, m.compareTo(n));
        }

        @Test
        @DisplayName("기간(term) 데이터가 Null 이면 Exception이 발생이 된다.")
        public void calculatePremium_fail1() {

            Product mock = Product.createBuilder().title("여행자 보험").warrants(getWarrants()).build();

            assertThrows(IllegalArgumentException.class, mock::calculatePremium);

        }

        @Test
        @DisplayName("담보 데이터가 Null 이면 Exception이 발생을 하게 된다.")
        public void calculatePremium_fail2() {

            Product mock = Product.createBuilder().title("여행자 보험").term(new ProductTerm(1, 3)).build();

            assertThrows(RuntimeException.class, mock::calculatePremium);
        }
    }

    private Set<Warrant> getWarrants() {
        Set<Warrant> mock = new HashSet<>();

        Warrant warrant = Warrant.createBuilder().title("상해치료").subscriptionAmount(new BigDecimal(1000000)).standardAmount(new BigDecimal(100)).build();

        mock.add(warrant);

        return mock;
    }
}
