package com.example.contract.doamin.entity.warrant;

import com.example.contract.domain.entity.warrant.Warrant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("담보 테이블에서")
class WarrantTest {

    @Nested
    @DisplayName("보험로 계산 로직은")
    class GetPremiumMethod {
        @ParameterizedTest(name = "보험료는 {0}, 담보데이터는 {1} 이다.")
        @ArgumentsSource(PremiumOkArgs.class)
        @DisplayName("성공을 하게 된다.")
        public void getPremium_ok(BigDecimal m , Warrant warrant) {
            BigDecimal n = warrant.getPremium();
            assertEquals(0, m.compareTo(n));
        }

        @ArgumentsSource(PremiumFailArgs.class)
        @DisplayName("예외를 발생을 하게 된다.")
        @ParameterizedTest(name = "담보 데이터를 {0} 으로 전달하면 예외가 발생이 된다.")
        public void getPremium_fail2(Warrant warrant) {
            assertThrows(IllegalArgumentException.class, warrant::getPremium);
        }
    }

    static class PremiumOkArgs implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
            return Stream.of(
                    Arguments.of(new BigDecimal(10000), Warrant.createBuilder().title("상해치료").subscriptionAmount(new BigDecimal(1000000)).standardAmount(new BigDecimal(100)).build())
            );
        }
    }

    static class PremiumFailArgs implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
            return Stream.of(
                    Arguments.of(Warrant.createBuilder().title("상해치료").subscriptionAmount(new BigDecimal(1000000)).build()),
                    Arguments.of(Warrant.createBuilder().title("상해치료").standardAmount(new BigDecimal(100)).build())
            );
        }
    }
}
