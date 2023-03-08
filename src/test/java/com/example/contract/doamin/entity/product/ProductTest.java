package com.example.contract.doamin.entity.product;

import com.example.contract.domain.entity.product.Product;
import com.example.contract.domain.entity.product.ProductTerm;
import com.example.contract.domain.entity.warrant.Warrant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

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

        @ArgumentsSource(CalculatePremiumFailArgs.class)
        @ParameterizedTest(name = "상품 데이터를 {0} 이거라면, 예외가 발생이 된다.")
        @DisplayName("실패을 하게 된다.")
        public void calculatePremium_fail2(Product mock) {
            assertThrows(RuntimeException.class, mock::calculatePremium);
        }
    }

    static class CalculatePremiumFailArgs implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
            return Stream.of(
                    Arguments.of( Product.createBuilder().title("여행자 보험").term(new ProductTerm(1, 3)).build()),
                    Arguments.of(Product.createBuilder().title("여행자 보험").warrants(getWarrants()).build())
            );
        }
    }

    private static Set<Warrant> getWarrants() {
        Set<Warrant> mock = new HashSet<>();

        Warrant warrant = Warrant.createBuilder().title("상해치료").subscriptionAmount(new BigDecimal(1000000)).standardAmount(new BigDecimal(100)).build();

        mock.add(warrant);

        return mock;
    }
}
