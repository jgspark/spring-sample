package com.example.contract.doamin;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("담보 테이블에서")
class WarrantTest {

    @Nested
    @DisplayName("보험로 계산 로직은")
    class GetPremiumMethod {

        @Test
        @DisplayName("성공을 하게 된다.")
        public void getPremium_ok() {

            Warrant warrant = Warrant.createBuilder().title("상해치료").subscriptionAmount(new BigDecimal(1000000)).standardAmount(new BigDecimal(100)).build();

            BigDecimal n = warrant.getPremium();

            BigDecimal m = new BigDecimal(10000);

            assertEquals(0, m.compareTo(n));
        }

        @Test
        @DisplayName("SubscriptionAmount 가 Null 이면 Exception 이 발생하게 된다.")
        public void getPremium_fail1() {

            Warrant warrant = Warrant.createBuilder().title("상해치료").standardAmount(new BigDecimal(100)).build();

            assertThrows(IllegalArgumentException.class, warrant::getPremium);

        }

        @Test
        @DisplayName("StandardAmount 가 Null 이면 Exception이 발생하게 된다.")
        public void getPremium_fail2() {

            Warrant warrant = Warrant.createBuilder().title("상해치료").subscriptionAmount(new BigDecimal(1000000)).build();

            assertThrows(IllegalArgumentException.class, warrant::getPremium);

        }
    }

}
