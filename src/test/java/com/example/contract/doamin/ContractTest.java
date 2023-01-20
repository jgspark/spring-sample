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
    @DisplayName("계약 데이터 변경이 될 때")
    public void update_ok() {

        Contract entity = getMock();

        Set<Warrant> warrants = new HashSet<>();

        warrants.add(Warrant.createBuilder().title("더미 데이터 1").build());
        warrants.add(Warrant.createBuilder().title("더미 데이터 2").build());

        Integer term = 1;

        ContractState state = ContractState.WITHDRAWAL;

        BigDecimal premium = new BigDecimal(2000);

        entity.update(warrants, term, state, premium);

        assertEquals( entity.getWarrants() , warrants);
        assertEquals( entity.getTerm() , term);
        assertEquals( entity.getState() , state);
        assertEquals(premium.compareTo(entity.getPremium()), 0);
    }

    @Test
    @DisplayName("계약의 상태기 기간 만료 일 때")
    public void isExpiration_true_case() {

        Contract entity = getMock();

        entity.update(new HashSet<>(), 1, ContractState.EXPIRATION, BigDecimal.ONE);

        assertTrue(entity.isExpiration());
    }

    @Test
    @DisplayName("계약의 상태기 기간 만료 가 아닐때")
    public void isExpiration_false_case() {

        Contract entity = getMock();

        entity.update(new HashSet<>(), 1, ContractState.NORMAL, BigDecimal.ONE);

        assertFalse(entity.isExpiration());
    }

    private Contract getMock() {
        return Contract.createBuilder().build();
    }
}
