package com.example.contract.repository;

import com.example.contract.doamin.Warrant;
import com.example.contract.mock.MockUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("담보 레파지토리 테스트 케이스")
@DataJpaTest
@ExtendWith(SpringExtension.class)
class WarrantRepositoryTest {

    @Autowired
    private WarrantRepository warrantRepository;

    @Test
    @Order(1)
    @DisplayName("단보 저장 테스트 케이스")
    public void save_ok() {

        Warrant mock = MockUtil.readJson("json/warrant/repository/save_ok.json", Warrant.class);

        Warrant entity = warrantRepository.save(mock);

        assertNotNull(entity.getId());
        assertEquals(entity.getTitle(), mock.getTitle());
        assertEquals(entity.getSubscriptionAmount(), mock.getSubscriptionAmount());
        assertEquals(entity.getStandardAmount(), mock.getStandardAmount());
    }

    @Nested
    @DisplayName("조회 테스트 케이스")
    public class Select {

        private Warrant mock;

        @BeforeEach
        public void init() {

            mock = warrantRepository.save(MockUtil.readJson("json/warrant/repository/select_init.json", Warrant.class));

            warrantRepository.flush();
        }

        @Test
        @Order(1)
        @DisplayName("담보 아이디 별 in 절 조회")
        public void findByIdIn_ok() {

            Set<Warrant> entities = warrantRepository.findByIdIn(Collections.singletonList(mock.getId()));

            for (Warrant entity : entities) {
                assertNotNull(entity.getId());
                assertEquals(entity.getTitle(), mock.getTitle());
                assertEquals(entity.getSubscriptionAmount(), mock.getSubscriptionAmount());
                assertEquals(entity.getStandardAmount(), mock.getStandardAmount());
            }
        }
    }
}
