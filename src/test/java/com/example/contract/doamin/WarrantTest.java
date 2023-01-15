package com.example.contract.doamin;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class WarrantTest {

    @Test
    @DisplayName("보험료 계산 테스트")
    public void getPremium_ok() {

        Warrant warrant = Warrant.createBuilder()
                .title("상해치료")
                .subscriptionAmount(new BigDecimal(1000000))
                .standardAmount(new BigDecimal(100))
                .build();

        BigDecimal n = warrant.getPremium();

        assertEquals(new BigDecimal(10000), n);
    }

    @Test
    @DisplayName("보험료 계산 subscriptionAmount 가 null 테스트")
    public void getPremium_fail1() {

        Warrant warrant = Warrant.createBuilder()
                .title("상해치료")
                .standardAmount(new BigDecimal(100))
                .build();

        assertThrows(IllegalArgumentException.class, () -> {
            BigDecimal n = warrant.getPremium();
        });

    }

    @Test
    @DisplayName("보험료 계산 standardAmount 가 null 테스트")
    public void getPremium_fail2() {

        Warrant warrant = Warrant.createBuilder()
                .title("상해치료")
                .subscriptionAmount(new BigDecimal(1000000))
                .build();

        assertThrows(IllegalArgumentException.class, () -> {
            BigDecimal n = warrant.getPremium();
        });

    }

}
