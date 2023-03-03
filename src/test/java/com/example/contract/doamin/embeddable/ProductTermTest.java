package com.example.contract.doamin.embeddable;

import com.example.contract.domain.product.ProductTerm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("상품 계약 기간 데이터 에서")
class ProductTermTest {


    @Nested
    @DisplayName("기간 데이터 체크 로직은")
    class CheckTermMethod {

        @Test
        @DisplayName("시작 계약 기간이 끝 계약 기간 보다 작으면 성공이 된다.")
        public void checkTerm_ok() {

            ProductTerm productTerm = new ProductTerm(1, 10);

            productTerm.checkTerm();
        }

        @Test
        @DisplayName("시작 계약 기간이 더 크면 실패 하게 된다.")
        public void checkTerm_fail1() {

            ProductTerm productTerm = new ProductTerm(10, 1);

            assertThrows(RuntimeException.class, productTerm::checkTerm);
        }

        @Test
        @DisplayName("시작 계약 기간이 Null 이면 실패 하게 된다.")
        public void checkTerm_fail2() {

            ProductTerm productTerm = new ProductTerm(null, 1);

            assertThrows(IllegalArgumentException.class, productTerm::checkTerm);
        }

        @Test
        @DisplayName("끝 계약 기간이 Null 이면 실패 하게 된다.")
        public void checkTerm_fail3() {

            ProductTerm productTerm = new ProductTerm(null, 1);

            assertThrows(IllegalArgumentException.class, productTerm::checkTerm);
        }

    }
}
