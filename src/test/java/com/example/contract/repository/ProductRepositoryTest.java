package com.example.contract.repository;

import com.example.contract.domain.entity.product.Product;
import com.example.contract.domain.entity.warrant.Warrant;
import com.example.contract.domain.mapper.EstimatedPremium;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.contract.mock.ConvertUtil.convert;
import static com.example.contract.mock.ConvertUtil.convertWarrant;
import static com.example.contract.mock.MockUtil.readJson;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("상품 레파지토리 에서")
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
        Map<String, Object> map = readJson("json/product/repository/init.json", Map.class);

        Warrant mock = convertWarrant((Map) map.get("warrant"));

        this.mockWarrant = warrantRepository.saveAndFlush(mock);
    }

    @Nested
    @DisplayName("저장 로직 은")
    class SaveMethod {

        @Test
        @Order(1)
        @DisplayName("성공적으로 실행이 된다.")
        public void save_ok() {

            Warrant findWarrant = warrantRepository.findById(mockWarrant.getId()).orElseThrow(RuntimeException::new);

            Product mock = convert(readJson("json/product/repository/save_ok.json", Product.class), findWarrant);

            Product entity = productRepository.save(mock);

            productRepository.flush();

            assertNotNull(entity.getId());
            assertEquals(entity.getTitle(), mock.getTitle());
            assertEquals(entity.getTerm(), mock.getTerm());
            assertEquals(entity.getWarrants(), mock.getWarrants());
        }

        @Test
        @Order(2)
        @DisplayName("계약 기간이 조건에 맞지 않는다면, 예외가 발생이 된다.")
        public void save_fail_case1() {

            Product mock = readJson("json/product/repository/save_fail_case1.json", Product.class);

            assertThrows(RuntimeException.class, () -> {
                productRepository.save(mock);
            });
        }

    }

    @Nested
    @DisplayName("조회 테스트 케이스 은")
    class Select {

        private Product mock;

        @BeforeEach
        public void init() {

            Warrant findWarrant = warrantRepository.findById(mockWarrant.getId()).orElseThrow(RuntimeException::new);

            mock = productRepository.save(convert(readJson("json/product/repository/findByIdAndWarrants_IdIn_ok.json", Product.class), findWarrant));

            productRepository.flush();
        }


        @Nested
        @DisplayName("상품 아이디 조회 메소드에서")
        class FindByIdMethod {

            @Test
            @DisplayName("성공적으로 테스트가 실행이 된다.")
            public void findById_ok() {

                EstimatedPremium entity = productRepository.findById(mock.getId(), EstimatedPremium.class).orElseThrow(RuntimeException::new);

                assertEquals(entity.getProductTitle(), mock.getTitle());
                assertEquals(entity.getTerm(), mock.getTerm().getRange());
                assertEquals(entity.getPremium(), mock.calculatePremium());
            }
        }

        @Nested
        @DisplayName("상품 아이디 그리고 담보 아이디들 조회 메소드 에서")
        class FindByIdAndWarrantsIdInMethod {

            @Test
            @DisplayName("성공 적으로 테스트 케이스가 실행이 된다.")
            public void findByIdAndWarrants_IdIn_ok() {

                Product entity = productRepository.findByIdAndWarrants_IdIn(mock.getId(), Collections.singleton(mockWarrant.getId())).orElseThrow(RuntimeException::new);

                assertEquals(entity.getId(), mock.getId());
                assertEquals(entity.getTitle(), mock.getTitle());
                assertEquals(entity.getTerm(), mock.getTerm());
                assertEquals(entity.getWarrants(), mock.getWarrants());
            }


            @Test
            @DisplayName("상품 아이디 와 담보 아이디 별 in 절 조회시 데이터가 비워있다면, 예외가 발생을 하게 된다.")
            public void findByIdAndWarrants_IdIn_fail1() {

                Long mockId = 10000000L;

                assertThrows(RuntimeException.class, () -> {
                    productRepository.findByIdAndWarrants_IdIn(mockId, Collections.singleton(mockWarrant.getId())).orElseThrow(RuntimeException::new);
                });

            }

            @Test
            @Order(4)
            @DisplayName("JPA Projection 을 사용을 할경우 성공 한다.")
            public void findByIdAndWarrants_IdIn_ok_projections() {

                EstimatedPremium entity = productRepository.findByIdAndWarrants_IdIn(mock.getId(), mock.getWarrants().stream().map(Warrant::getId).collect(Collectors.toSet()), EstimatedPremium.class).orElseThrow(RuntimeException::new);

                assertEquals(entity.getProductTitle(), mock.getTitle());
                assertEquals(entity.getTerm(), mock.getTerm().getRange());
                assertEquals(entity.getPremium(), mock.calculatePremium());
            }
        }
    }
}
