package com.example.contract.doamin;

import com.example.contract.domain.contract.Contract;
import com.example.contract.domain.warrant.Warrant;
import com.example.contract.domain.contract.ContractState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("계약 테이블 에서")
class ContractTest {

    @Nested
    @DisplayName("업데이트 메소드 은")
    class UpdateMethod {

        @Test
        @DisplayName("성공적으로 데이터를 변경할 수 있다.")
        public void update_ok() {

            Contract entity = getMock();

            Set<Warrant> warrants = new HashSet<>();

            warrants.add(Warrant.createBuilder().title("더미 데이터 1").build());
            warrants.add(Warrant.createBuilder().title("더미 데이터 2").build());

            Integer term = 1;

            ContractState state = ContractState.WITHDRAWAL;

            BigDecimal premium = new BigDecimal(2000);

            entity.update(warrants, term, state, premium);

            assertEquals(entity.getWarrants(), warrants);
            assertEquals(entity.getTerm(), term);
            assertEquals(entity.getState(), state);
            assertEquals(premium.compareTo(entity.getPremium()), 0);
        }

    }

    @Nested
    @DisplayName("기간 만료 체크 로직은")
    class IsExpirationMethod {

        @Test
        @DisplayName("성공 할 때는 데이터가 기간만료라면 true를 리턴 한다.")
        public void isExpiration_true_case() {

            Contract entity = getMock();

            entity.update(new HashSet<>(), 1, ContractState.EXPIRATION, BigDecimal.ONE);

            assertTrue(entity.isExpiration());
        }

        @Test
        @DisplayName("성공 할 때는 데이터가 기간만료가 아니라면 false 리턴을 한다.")
        public void isExpiration_false_case() {

            Contract entity = getMock();

            entity.update(new HashSet<>(), 1, ContractState.NORMAL, BigDecimal.ONE);

            assertFalse(entity.isExpiration());
        }

    }

    private Contract getMock() {
        return Contract.createBuilder().build();
    }
}
