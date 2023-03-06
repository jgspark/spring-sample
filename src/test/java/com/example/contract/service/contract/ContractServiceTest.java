package com.example.contract.service.contract;

import com.example.contract.dto.model.contract.ContractSaveModel;
import com.example.contract.dto.model.contract.ContractUpdateModel;
import com.example.contract.exception.AppException;
import com.example.contract.domain.contract.Contract;
import com.example.contract.domain.product.Product;
import com.example.contract.domain.warrant.Warrant;
import com.example.contract.mock.contract.ContractDetailImpl;
import com.example.contract.repository.ContractRepository;
import com.example.contract.repository.ProductRepository;
import com.example.contract.dto.mapper.ContractDetail;
import com.example.contract.controller.request.ContractSaveRequest;
import com.example.contract.controller.request.ContractUpdateRequest;
import com.example.contract.dto.mapper.WarrantInfo;
import com.example.contract.service.contract.ContractServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;

import static com.example.contract.mock.ConvertUtil.*;
import static com.example.contract.mock.MockUtil.readJson;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("계약 서비스 레이어에서")
@ExtendWith(MockitoExtension.class)
class ContractServiceTest {

    private ContractService contractService;

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void init() {
        contractService = new ContractServiceImpl(contractRepository, productRepository);
    }

    @Nested
    @DisplayName("저장 로직 은")
    class CreatedMethod {

        @Test
        @DisplayName("성공적으로 테스트 케이스가 실행을 하게 된다.")
        public void created_ok() {

            Map<String, Object> mockMap = readJson("json/contract/service/created_ok.json", Map.class);

            Contract mock = convertContract((Map) mockMap.get("contract"));

            Warrant mockWarrant = convertWarrant((Map) mockMap.get("warrant"));

            Optional<Product> mockProductOptional = Optional.of(convert(convertProduct((Map) mockMap.get("product")), mockWarrant));

            ContractSaveRequest req = readJson("json/contract/service/contract_save_request.json", ContractSaveRequest.class);

            ContractSaveModel dto = new ContractSaveModel(req);

            given(productRepository.findByIdAndWarrants_IdIn(any(), any())).willReturn(mockProductOptional);

            given(contractRepository.save(any())).willReturn(mock);

            Contract entity = contractService.created(dto);

            then(productRepository).should().findByIdAndWarrants_IdIn(any(), any());
            then(contractRepository).should().save(any());

            assertEquals(entity.getId(), mock.getId());
            assertEquals(entity.getTerm(), mock.getTerm());
            assertEquals(entity.getStartDate(), mock.getStartDate());
            assertEquals(entity.getEndDate(), mock.getEndDate());
            assertEquals(entity.getPremium(), mock.getPremium());
            assertEquals(entity.getProduct(), mock.getProduct());
            assertEquals(entity.getWarrants(), mock.getWarrants());
        }

        @Test
        @DisplayName("상품 조회시 상품 데이터가 없으면, 예외가 발생을 하게 된다.")
        public void created_fail1() {

            ContractSaveRequest req = readJson("json/contract/service/contract_save_request.json", ContractSaveRequest.class);

            ContractSaveModel dto = new ContractSaveModel(req);

            given(productRepository.findByIdAndWarrants_IdIn(any(), any())).willReturn(Optional.empty());

            assertThrows(RuntimeException.class, () -> contractService.created(dto));
        }
    }

    @Nested
    @DisplayName("계약 단일 조회 로직 은")
    class GetOneMethod {
        @Test
        @DisplayName("성공적으로 데이터를 조회하게 된다.")
        public void getOne_ok() {

            long mockId = 1L;

            Optional<ContractDetail> mockOptional = Optional.of(new ContractDetailImpl(readJson("json/contract/service/getOne_ok.json", Contract.class)));

            given(contractRepository.findById(any(), eq(ContractDetail.class))).willReturn(mockOptional);

            ContractDetail entity = contractService.getOne(mockId).orElseThrow(RuntimeException::new);

            then(contractRepository).should().findById(any(), eq(ContractDetail.class));

            ContractDetail mock = mockOptional.get();

            assertEquals(entity.getId(), mock.getId());
            assertEquals(entity.getTerm(), mock.getTerm());
            assertEquals(entity.getStartDate(), mock.getStartDate());
            assertEquals(entity.getEndDate(), mock.getEndDate());
            assertEquals(entity.getPremium(), mock.getPremium());
            assertEquals(entity.getProduct().getId(), mock.getProduct().getId());
            assertEquals(entity.getProduct().getTitle(), mock.getProduct().getTitle());
            assertEquals(entity.getProduct().getRange(), mock.getProduct().getRange());

            entity.getWarrants().forEach(w -> {

                WarrantInfo findMock = mock.getWarrants().stream().filter(m -> m.getId().equals(w.getId())).findFirst().orElseThrow(RuntimeException::new);

                assertEquals(w.getTitle(), findMock.getTitle());
                assertEquals(w.getSubscriptionAmount(), findMock.getSubscriptionAmount());
                assertEquals(w.getStandardAmount(), findMock.getStandardAmount());
            });
        }

        @Test
        @DisplayName("조회 되지 않는 데이터를 반환을 하게 되면, isPresent 메소드는 false 를 반환을 하게 된다.")
        public void getOne_fail1() {

            long mockId = 1L;

            Optional<ContractDetail> mockOptional = Optional.empty();

            given(contractRepository.findById(any(), eq(ContractDetail.class))).willReturn(mockOptional);

            @NotNull Optional<ContractDetail> entity = contractService.getOne(mockId);

            then(contractRepository).should().findById(any(), eq(ContractDetail.class));

            assertFalse(entity.isPresent());
        }

    }

    @Nested
    @DisplayName("업데이트 로직 은")
    class UpdateMethod {

        @Test
        @DisplayName("성공 적으로 테스트 케이스를 작성을 하게 된다.")
        public void update_ok() {

            Long id = 1L;

            Map<String, Object> mockMap = readJson("json/contract/service/update_ok.json", Map.class);

            Optional<Contract> mockOptional = Optional.of(convertContract((Map) mockMap.get("contract")));

            Warrant mockWarrant = convertWarrant((Map) mockMap.get("warrant"));

            Optional<Product> mockProductOptional = Optional.of(convert(convertProduct((Map) mockMap.get("product")), mockWarrant));

            given(contractRepository.findById(any())).willReturn(mockOptional);

            given(productRepository.findByIdAndWarrants_IdIn(any(), any())).willReturn(mockProductOptional);

            ContractUpdateRequest req = readJson("json/contract/service/contract_update_request.json", ContractUpdateRequest.class);

            ContractUpdateModel dto = new ContractUpdateModel(id, req);

            Contract entity = contractService.update(dto);

            Contract mock = mockOptional.get();

            assertEquals(entity.getId(), mock.getId());
            assertEquals(entity.getTerm(), mock.getTerm());
            assertEquals(entity.getStartDate(), mock.getStartDate());
            assertEquals(entity.getEndDate(), mock.getEndDate());
            assertEquals(entity.getPremium(), mock.getPremium());
            assertEquals(entity.getProduct(), mock.getProduct());
            assertEquals(entity.getWarrants(), mock.getWarrants());
        }

        @Test
        @DisplayName("계약 데이터가 없다면, AppException이 발생이 되게 된다.")
        public void update_fail1() {

            Long id = 1L;

            Map<String, Object> mockMap = readJson("json/contract/service/update_ok.json", Map.class);

            Optional<Contract> mockOptional = Optional.of(convertContractByUpdate((Map) mockMap.get("contract")));

            given(contractRepository.findById(any())).willReturn(mockOptional);

            ContractUpdateRequest req = readJson("json/contract/service/contract_update_request.json", ContractUpdateRequest.class);

            ContractUpdateModel dto = new ContractUpdateModel(id, req);

            assertThrows(AppException.class, () -> contractService.update(dto));
        }
    }


}
