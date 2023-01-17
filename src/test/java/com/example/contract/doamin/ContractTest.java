package com.example.contract.doamin;

import com.example.contract.enums.ContractState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ContractTest {

    @Test
    @DisplayName("거래 데이터 변경이 될 때")
    public void update_ok() {

        Contract mock = getMock();

        Set<Warrant> warrants = new HashSet<>();

        warrants.add(Warrant.createBuilder().title("더미 데이터 1").build());
        warrants.add(Warrant.createBuilder().title("더미 데이터 2").build());

        Integer term = 1;

        ContractState state = ContractState.WITHDRAWAL;

        BigDecimal premium = new BigDecimal(2000);

        mock.update(warrants, term, state, premium);

        assertEquals(warrants, mock.getWarrants());
        assertEquals(term, mock.getTerm());
        assertEquals(state, mock.getState());
        assertEquals(premium.compareTo(mock.getPremium()), 0);
    }

    @Test
    @DisplayName("거래의 상태기 기간 만료 일 때")
    public void isExpiration_true_case() {

        Contract mock = getMock();

        mock.update(new HashSet<>(), 1, ContractState.EXPIRATION, BigDecimal.ONE);

        assertTrue(mock.isExpiration());
    }

    @Test
    @DisplayName("거래의 상태기 기간 만료 가 아닐때")
    public void isExpiration_false_case() {

        Contract mock = getMock();

        mock.update(new HashSet<>(), 1, ContractState.NORMAL, BigDecimal.ONE);

        assertFalse(mock.isExpiration());
    }

    private Contract getMock() {
        return Contract.createBuilder().build();
    }
}
