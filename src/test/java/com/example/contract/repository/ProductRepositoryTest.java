package com.example.contract.repository;

import com.example.contract.doamin.Product;
import com.example.contract.doamin.Warrant;
import com.example.contract.mock.MockUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WarrantRepository warrantRepository;

    private Warrant mockWarrant;

    @BeforeEach
    public void init() {
        Map<String, Object> map = MockUtil.readJson("json/product/repository/init.json", Map.class);

        Warrant mock = MockUtil.convertWarrant((Map) map.get("warrant"));

        this.mockWarrant = warrantRepository.saveAndFlush(mock);
    }

    @Test
    @Order(1)
    @DisplayName("상품 생성")
    public void save_ok() {

        Warrant findWarrant = warrantRepository.findById(mockWarrant.getId()).orElseThrow(RuntimeException::new);

        Product mock = MockUtil.convert(MockUtil.readJson("json/product/repository/save_ok.json", Product.class), findWarrant);

        Product entity = productRepository.save(mock);

        productRepository.flush();

        assertNotNull(entity.getId());
        assertEquals(mock.getTitle(), entity.getTitle());
        assertEquals(mock.getTerm(), entity.getTerm());
        assertEquals(entity.getWarrants(), mock.getWarrants());
    }

    @Test
    @Order(2)
    @DisplayName("계약 기간이 조건에 맞지 않는 케이스")
    public void save_fail_case1() {

        Product mock = MockUtil.readJson("json/product/repository/save_fail_case1.json", Product.class);

        assertThrows(RuntimeException.class, () -> {
            productRepository.save(mock);
        });
    }

    @Nested
    @DisplayName("조회 테스트 케이스")
    public class Select {

        private Product mock;

        @BeforeEach
        public void init() {

            Warrant findWarrant = warrantRepository.findById(mockWarrant.getId()).orElseThrow(RuntimeException::new);

            mock = productRepository.save(MockUtil.convert(MockUtil.readJson("json/product/repository/findByIdAndWarrants_IdIn_ok.json", Product.class), findWarrant));

            productRepository.flush();
        }

        @Test
        @Order(1)
        @DisplayName("상품 아이디 와 담보 아이디 별 in 절 조회 성공 케이스")
        public void findByIdAndWarrants_IdIn_ok() {

            Product entity = productRepository.findByIdAndWarrants_IdIn(mock.getId(), Collections.singleton(mockWarrant.getId())).orElseThrow(RuntimeException::new);

            assertEquals(entity.getId(), mock.getId());
            assertEquals(entity.getTitle(), mock.getTitle());
            assertEquals(entity.getTerm(), mock.getTerm());
            assertEquals(entity.getWarrants(), mock.getWarrants());
        }

        @Test
        @Order(2)
        @DisplayName("상품 아이디 와 담보 아이디 별 in 절 조회 실패 케이스")
        public void findByIdAndWarrants_IdIn_fail1() {

            Long mockId = 10000000L;

            assertThrows(RuntimeException.class, () -> {
                productRepository.findByIdAndWarrants_IdIn(mockId, Collections.singleton(mockWarrant.getId())).orElseThrow(RuntimeException::new);
            });

        }


    }
}
