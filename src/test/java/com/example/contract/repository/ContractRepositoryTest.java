package com.example.contract.repository;

import com.example.contract.doamin.Contract;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class ContractRepositoryTest {

    @Autowired
    private ContractRepository contractRepository;

    @Test
    @DisplayName("계약 생성 성공 케이스")
    public void save_ok() {

        Contract mock = Contract.createBuilder().build();

        Contract entity = contractRepository.save(mock);

        assertNotNull(entity.getId());
        assertEquals(entity.getState(), mock.getState());
    }

}
