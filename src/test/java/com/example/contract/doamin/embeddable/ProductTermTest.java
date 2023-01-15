package com.example.contract.doamin.embeddable;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTermTest {

    @Test
    @DisplayName("start month 가 더 작을 경우 성공")
    public void checkTerm_ok() {

        ProductTerm productTerm = new ProductTerm(1, 10);

        productTerm.checkTerm();
    }

    @Test
    @DisplayName("start month 가 더 클 경우 실패")
    public void checkTerm_fail1() {

        ProductTerm productTerm = new ProductTerm(10, 1);

        assertThrows(RuntimeException.class, () -> {
            productTerm.checkTerm();
        });
    }

    @Test
    @DisplayName("start month 가 null 일떄")
    public void checkTerm_fail2() {

        ProductTerm productTerm = new ProductTerm(null, 1);

        assertThrows(IllegalArgumentException.class, () -> {
            productTerm.checkTerm();
        });
    }

    @Test
    @DisplayName("end month 가 null 일떄")
    public void checkTerm_fail3() {

        ProductTerm productTerm = new ProductTerm(null, 1);

        assertThrows(IllegalArgumentException.class, () -> {
            productTerm.checkTerm();
        });
    }

}
