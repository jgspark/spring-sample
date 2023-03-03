package com.example.contract.repository;

import com.example.contract.exception.DataNotFoundException;
import com.example.contract.domain.contract.Contract;
import com.example.contract.domain.product.Product;
import com.example.contract.domain.warrant.Warrant;
import com.example.contract.domain.contract.ContractState;
import com.example.contract.dto.mapper.ContractDetail;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static com.example.contract.mock.ConvertUtil.*;
import static com.example.contract.mock.MockUtil.readJson;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("계약 레파지터리에서")
@DataJpaTest
@ExtendWith(SpringExtension.class)
class ContractRepositoryTest {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private WarrantRepository warrantRepository;

    @Autowired
    private ProductRepository productRepository;

    private Product mockProduct;

    private Warrant mockWarrant;

    @BeforeEach
    public void init() {
        Map<String, Object> mockMap = readJson("json/contract/repository/init.json", Map.class);
        mockWarrant = warrantRepository.saveAndFlush(convertWarrant((Map) mockMap.get("warrant")));
        mockProduct = productRepository.saveAndFlush(convert(convertProduct((Map) mockMap.get("product")), mockWarrant));
    }

    @Nested
    @DisplayName("저장 로직 은")
    class SaveMethod {

        @Test
        @Order(1)
        @DisplayName("계약 생성 성공 케이스")
        public void save_ok() {

            Contract mock = convert(readJson("json/contract/repository/save_ok.json", Contract.class), mockProduct, mockWarrant);

            Contract entity = contractRepository.save(mock);

            contractRepository.flush();

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
        @Order(2)
        @DisplayName("계약 기간이 0 또는 null 일 때 케이스")
        public void save_fail_case1() {

            Contract mock = readJson("json/contract/repository/save_fail_case1.json", Contract.class);

            assertThrows(DataNotFoundException.class, () -> {
                contractRepository.save(mock);
            });
        }

    }

    @Nested
    @DisplayName("계약 아이디로 조회하는 로직 은")
    public class FindByIdMethod {

        private Contract mock;

        @BeforeEach
        public void init() {
            mock = contractRepository.save(convert(readJson("json/contract/repository/save_ok.json", Contract.class), mockProduct, mockWarrant));
        }

        @Test
        @DisplayName("성공적으로 테스트케이스를 통과를 한다.")
        public void findById_ok() {

            ContractDetail entity = contractRepository.findById(mock.getId(), ContractDetail.class).orElseThrow(RuntimeException::new);

            assertEquals(entity.getId(), mock.getId());
            assertEquals(entity.getTerm(), mock.getTerm());
            assertEquals(convert(entity.getStartDate()), convert(mock.getStartDate()));
            assertEquals(convert(entity.getEndDate()), convert(mock.getEndDate()));
            assertEquals(convert(entity.getPremium()), convert(mock.getPremium()));
            assertEquals(entity.getState(), mock.getState());
            assertEquals(entity.getProduct().getId(), mock.getProduct().getId());
            assertEquals(entity.getProduct().getTitle(), mock.getProduct().getTitle());
            assertEquals(entity.getProduct().getRange(), mock.getProduct().getTerm().getRange());

            entity.getWarrants().forEach(w -> {

                Warrant findWarrant = mock.getWarrants().stream().filter(m -> m.getId().equals(w.getId())).findFirst().orElseThrow(RuntimeException::new);

                assertEquals(w.getTitle(), findWarrant.getTitle());
                assertEquals(w.getSubscriptionAmount(), findWarrant.getSubscriptionAmount());
                assertEquals(w.getStandardAmount(), findWarrant.getStandardAmount());
            });

        }

        @Test
        @DisplayName("아이디가 없으면 Exception이 발생이 된다.")
        public void findById_fail1() {

            Long mockId = 100000L;

            assertThrows(RuntimeException.class, () -> {
                contractRepository.findById(mockId, ContractDetail.class).orElseThrow(RuntimeException::new);
            });
        }
    }
}
