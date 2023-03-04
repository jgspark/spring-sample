package com.example.contract.doamin.product;

import com.example.contract.domain.product.ProductTerm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("상품 계약 기간 데이터 에서")
class ProductTermTest {


    @Nested
    @DisplayName("기간 데이터 체크 로직은")
    class CheckTermMethod {

        @Test
        @DisplayName("시작 계약 기간이 끝 계약 기간 보다 작으면 성공이 된다.")
        public void checkTerm_ok() {

            ProductTerm productTerm = new ProductTerm(1, 10);

            assertDoesNotThrow(productTerm::checkTerm);
        }

        @DisplayName("해당 케이스 별로 예외 처리를 한다.")
        @ParameterizedTest(name = "시작 월 데이터가 {0} 이면서, 끝 월 데이터 가 {1}일떄")
        @ArgumentsSource(CheckTermFailArgs.class)
        public void checkTerm_fail3(Integer startMonth , Integer endMonth) {

            ProductTerm productTerm = new ProductTerm(startMonth, endMonth);

            assertThrows(Exception.class, productTerm::checkTerm);
        }

    }

    static class CheckTermFailArgs implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
            return Stream.of(
                    Arguments.of(null , 1),
                    Arguments.of(1 , null),
                    Arguments.of(10 , 1)
            );
        }
    }

}
