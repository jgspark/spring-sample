package com.example.contract.service;

import com.example.contract.config.exception.AppException;
import com.example.contract.doamin.Contract;
import com.example.contract.doamin.Product;
import com.example.contract.doamin.Warrant;
import com.example.contract.mock.ContractDetailImpl;
import com.example.contract.repository.ContractRepository;
import com.example.contract.repository.ProductRepository;
import com.example.contract.web.dto.ContractDetail;
import com.example.contract.web.dto.ContractSaveRequest;
import com.example.contract.web.dto.ContractUpdateRequest;
import com.example.contract.web.dto.WarrantInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;

import static com.example.contract.mock.ConvertUtil.*;
import static com.example.contract.mock.MockUtil.readJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("거래 서비스 레이어 테스트 케이스")
@ExtendWith(MockitoExtension.class)
class ContractServiceTest {

    private ContractService contractService;

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void init() {
        contractService = new ContractService(contractRepository, productRepository);
    }

    @Test
    @DisplayName("계약 저장 성공 케이스")
    public void created_ok() {

        Map<String, Object> mockMap = readJson("json/contract/service/created_ok.json", Map.class);

        Contract mock = convertContract((Map) mockMap.get("contract"));

        Warrant mockWarrant = convertWarrant((Map) mockMap.get("warrant"));

        Optional<Product> mockProductOptional = Optional.of(convert(convertProduct((Map) mockMap.get("product")), mockWarrant));

        ContractSaveRequest dto = readJson("json/contract/service/contract_save_request.json", ContractSaveRequest.class);

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
    @DisplayName("계약 저장 상품 혹은 담보가 없을 경우 실패 케이스")
    public void created_fail1() {

        ContractSaveRequest dto = readJson("json/contract/service/contract_save_request.json", ContractSaveRequest.class);

        given(productRepository.findByIdAndWarrants_IdIn(any(), any())).willReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            contractService.created(dto);
        });
    }

    @Test
    @DisplayName("계약 상세 조회 테스트 케이스")
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
    @DisplayName("업데이트 성공 케이스")
    public void update_ok() {

        Long id = 1L;

        Map<String, Object> mockMap = readJson("json/contract/service/update_ok.json", Map.class);

        Optional<Contract> mockOptional = Optional.of(convertContract((Map) mockMap.get("contract")));

        Warrant mockWarrant = convertWarrant((Map) mockMap.get("warrant"));

        Optional<Product> mockProductOptional = Optional.of(convert(convertProduct((Map) mockMap.get("product")), mockWarrant));

        given(contractRepository.findById(any())).willReturn(mockOptional);

        given(productRepository.findByIdAndWarrants_IdIn(any(), any())).willReturn(mockProductOptional);

        ContractUpdateRequest dto = readJson("json/contract/service/contract_update_request.json", ContractUpdateRequest.class);

        Contract entity = contractService.update(id, dto);

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
    @DisplayName("업데이트 실패 케이스")
    public void update_fail1() {

        Long id = 1L;

        Map<String, Object> mockMap = readJson("json/contract/service/update_ok.json", Map.class);

        Optional<Contract> mockOptional = Optional.of(convertContractByUpdate((Map) mockMap.get("contract")));

        given(contractRepository.findById(any())).willReturn(mockOptional);

        ContractUpdateRequest dto = readJson("json/contract/service/contract_update_request.json", ContractUpdateRequest.class);

        assertThrows(AppException.class, () -> {
            contractService.update(id, dto);
        });
    }
}
