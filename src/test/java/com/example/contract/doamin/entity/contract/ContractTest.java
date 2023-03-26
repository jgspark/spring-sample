package com.example.contract.doamin.entity.contract;

import com.example.contract.domain.entity.contract.Contract;
import com.example.contract.domain.entity.contract.ContractState;
import com.example.contract.domain.entity.warrant.Warrant;
import com.example.contract.exception.AppException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("계약 테이블 에서")
class ContractTest {

    @Nested
    @DisplayName("업데이트 메소드 은")
    class UpdateMethod {

        @DisplayName("성공적으로 데이터를 변경할 수 있다.")
        @ParameterizedTest(name = "계약의 상태는 {0}, 보험금은 {1}, 기간은 {2}, 담보의 명칭은 {3}")
        @ArgumentsSource(UpdateOkArgs.class)
        public void update_ok(ContractState state , BigDecimal premium , Integer term ,Collection<String> titleList) {

            Contract entity = getMock();

            Set<Warrant> warrants = new HashSet<>();

            for (String title :titleList){
                Warrant warrant = Warrant.createBuilder()
                        .title(title)
                        .build();

                warrants.add(warrant);
            }

            entity.update(warrants, term, state, premium);

            assertEquals(entity.getWarrants(), warrants);
            assertEquals(entity.getTerm(), term);
            assertEquals(entity.getState(), state);
            assertEquals(premium.compareTo(entity.getPremium()), 0);
        }

        @DisplayName("실패할 수 있다")
        @ParameterizedTest(name = "계약의 상태는 {0}, 보험금은 {1}, 기간은 {2}, 담보의 명칭은 {3}")
        @ArgumentsSource(UpdateFailArgs.class)
        public void update_fail(ContractState state , BigDecimal premium , Integer term ,Collection<String> titleList) {

            Contract entity = getMock();

            Set<Warrant> warrants = new HashSet<>();

            for (String title :titleList){
                Warrant warrant = Warrant.createBuilder()
                        .title(title)
                        .build();

                warrants.add(warrant);
            }

            assertThrows(IllegalArgumentException.class , () -> entity.update(warrants, term, state, premium));
        }

    }

    @Nested
    @DisplayName("기간 만료 체크 로직은")
    class CheckedExpirationMethod {

        @Test
        @DisplayName("성공 할 때는 데이터가 기간만료라면 true를 리턴 한다.")
        public void isExpiration_true_case() {

            Contract entity = getMock();

            entity.update(new HashSet<>(), 1, ContractState.EXPIRATION, BigDecimal.ONE);

            assertThrows(AppException.class , entity::checkedExpiration);
        }

        @DisplayName("성공 할 때는 데이터가 기간만료가 아니라면 false 리턴을 한다.")
        @ParameterizedTest(name = "계약의 상태 값이 {0} 일떄 성공")
        @ArgumentsSource(ContractStateArgs.class)
        public void isExpiration_false_case(ContractState state) {

            Contract entity = getMock();

            entity.update(new HashSet<>(), 1, state, BigDecimal.ONE);

            assertDoesNotThrow(entity::checkedExpiration);
        }

    }

    private Contract getMock() {
        return Contract.createBuilder().build();
    }

    static class ContractStateArgs implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
            return Stream.of(
                    Arguments.of(ContractState.NORMAL),
                    Arguments.of(ContractState.WITHDRAWAL)
                    );
        }
    }

    static class UpdateOkArgs implements ArgumentsProvider{

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
            return Stream.of(
                    Arguments.of(ContractState.EXPIRATION , new BigDecimal(2000) , 1 , Arrays.asList("타이틀 1" , "타이틀22")),
                    Arguments.of(ContractState.NORMAL , new BigDecimal(20000000) , 12 , Collections.emptyList())
            );
        }
    }
    static class UpdateFailArgs implements ArgumentsProvider{

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
            return Stream.of(
                    Arguments.of(null , BigDecimal.ZERO , null , Collections.emptyList())
            );
        }
    }

}
