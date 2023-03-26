package com.example.contract.repository;

import com.example.contract.domain.entity.warrant.Warrant;
import com.example.contract.mock.MockUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("담보 레파지토리 에서")
@DataJpaTest
@ExtendWith(SpringExtension.class)
class WarrantJpaRepositoryTest {

    @Autowired
    private WarrantRepository warrantRepository;

    @Nested
    @DisplayName("저장 로직 은")
    class SaveMethod {

        @Test
        @DisplayName("성공적으로 테스트 케이스가 실행이 된다.")
        public void save_ok() {

            Warrant mock = MockUtil.readJson("json/warrant/repository/save_ok.json", Warrant.class);

            Warrant entity = warrantRepository.save(mock);

            assertNotNull(entity.getId());
            assertEquals(entity.getTitle(), mock.getTitle());
            assertEquals(entity.getSubscriptionAmount(), mock.getSubscriptionAmount());
            assertEquals(entity.getStandardAmount(), mock.getStandardAmount());
        }
    }

    @Nested
    @DisplayName("담보 아이디들을 조회하는 로직 은")
    public class FindByIdIn {

        private Warrant mock;

        @BeforeEach
        public void init() {

            mock = warrantRepository.save(MockUtil.readJson("json/warrant/repository/select_init.json", Warrant.class));

        }

        @Test
        @DisplayName("성공적으로 테스트 케이스를 실행을 하게 된다.")
        public void findByIdIn_ok() {

            Set<Warrant> entities = warrantRepository.findByIdIn(Collections.singletonList(mock.getId()));

            for (Warrant entity : entities) {
                assertNotNull(entity.getId());
                assertEquals(entity.getTitle(), mock.getTitle());
                assertEquals(entity.getSubscriptionAmount(), mock.getSubscriptionAmount());
                assertEquals(entity.getStandardAmount(), mock.getStandardAmount());
            }
        }

        @Test
        @DisplayName("없는 아이디 값을 조회를 하게 되면, empty list 를 반환을 하게 된다.")
        public void findByIdIn_fail1() {

            Long mockId = 100000L;

            Set<Warrant> entities = warrantRepository.findByIdIn(Collections.singletonList(mockId));

            assertNotNull(entities);
            assertEquals(0, entities.size());
        }
    }
}
