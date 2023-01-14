package com.example.contract.repository;

import com.example.contract.doamin.Contract;
import com.example.contract.enums.ContractState;
import com.example.contract.mock.MockUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class ContractRepositoryTest {

    @Autowired
    private ContractRepository contractRepository;

    @Test
    @DisplayName("계약 생성 성공 케이스")
    public void save_ok() {

        Contract mock = MockUtil.readJson("json/contract/save_ok.json", Contract.class);

        Contract entity = contractRepository.save(mock);

        assertNotNull(entity.getId());
        assertEquals(entity.getTerm(), mock.getTerm());
        assertEquals(entity.getStartDate(), mock.getStartDate());
        assertEquals(entity.getEndDate(), mock.getEndDate());
        assertEquals(entity.getPremium(), mock.getPremium());
        assertEquals(entity.getState(), ContractState.NORMAL);
        assertEquals(entity.getProduct(), mock.getProduct());
        assertEquals(entity.getWarrants(), mock.getWarrants());
    }

    @Test
    @DisplayName("계약 기간이 0 또는 null 일 때 케이스")
    public void save_fail_case1() {

        Contract mock = MockUtil.readJson("json/contract/save_fail_case1.json", Contract.class);

        assertThrows(NullPointerException.class, () -> {
            contractRepository.save(mock);
        });
    }

}
