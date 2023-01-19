package com.example.contract.service;

import com.example.contract.config.exception.AppException;
import com.example.contract.doamin.Product;
import com.example.contract.doamin.Warrant;
import com.example.contract.mock.EstimatedPremiumImpl;
import com.example.contract.repository.ProductRepository;
import com.example.contract.repository.WarrantRepository;
import com.example.contract.web.dto.EstimatedPremium;
import com.example.contract.web.dto.ProductSaveRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.contract.mock.ConvertUtil.convert;
import static com.example.contract.mock.ConvertUtil.convertProduct;
import static com.example.contract.mock.MockUtil.readJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("상품 서비스 레이어 테스트 케이스")
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private WarrantRepository warrantRepository;

    @BeforeEach
    public void init() {
        productService = new ProductService(productRepository, warrantRepository);
    }

    @Test
    @DisplayName("상품 성공 테스트 케이스")
    public void created_ok() {

        Map map = readJson("json/product/service/created_ok.json", Map.class);

        Set<Warrant> warrantSet = convert(map.get("warrant"));

        Product mock = convert(convertProduct((Map) map.get("product")), warrantSet);

        given(warrantRepository.findByIdIn(any())).willReturn(warrantSet);

        given(productRepository.save(any())).willReturn(mock);

        ProductSaveRequest dto = readJson("json/product/service/product_save_request.json", ProductSaveRequest.class);

        Product entity = productService.created(dto);

        then(warrantRepository).should().findByIdIn(any());
        then(productRepository).should().save(any());

        assertEquals(entity.getTitle(), mock.getTitle());
        assertEquals(entity.getTerm(), mock.getTerm());
        assertEquals(entity.getWarrants(), mock.getWarrants());
    }

    @Test
    @DisplayName("상품 실패 테스트 케이스")
    public void created_fail1() {

        given(warrantRepository.findByIdIn(any())).willReturn(new HashSet<>());

        ProductSaveRequest dto = readJson("json/product/service/product_save_request.json", ProductSaveRequest.class);

        assertThrows(AppException.class, () -> {
            productService.created(dto);
        });
    }

    @Test
    @DisplayName("총 보험료 예상 금액 로직 성공 테스트 케이스 담보가 있을 때 케이스")
    public void getEstimatedPremium_ok1() {

        Optional<EstimatedPremium> mockOptional = Optional.of(new EstimatedPremiumImpl(readJson("json/product/service/getEstimatedPremium_ok1.json", Product.class)));

        given(productRepository.findByIdAndWarrants_IdIn(anyLong(), any(), eq(EstimatedPremium.class))).willReturn(mockOptional);

        Map<String, Object> dto = readJson("json/product/service/getEstimatedPremium_ok1_dto.json", Map.class);

        List<Long> warrantIds = ((ArrayList<Integer>) dto.get("warrantIds")).stream().map(Integer::longValue).collect(Collectors.toList());

        Integer productId = (Integer) dto.get("productId");

        Optional<EstimatedPremium> entityOptional = productService.getEstimatedPremium(productId.longValue(), warrantIds);

        then(productRepository).should().findByIdAndWarrants_IdIn(anyLong(), any(), eq(EstimatedPremium.class));

        EstimatedPremium entity = entityOptional.get();

        EstimatedPremium mock = mockOptional.get();

        assertEquals(entity.getProductTitle(), mock.getProductTitle());
        assertEquals(entity.getTerm(), mock.getTerm());
        assertEquals(entity.getPremium(), mock.getPremium());
    }

    @Test
    @DisplayName("총 보험료 예상 금액 로직 성공 테스트 케이스 전체 담보 케이스")
    public void getEstimatedPremium_ok2() {

        Optional<EstimatedPremium> mockOptional = Optional.of(new EstimatedPremiumImpl(readJson("json/product/service/getEstimatedPremium_ok2.json", Product.class)));

        given(productRepository.findById(anyLong(), eq(EstimatedPremium.class))).willReturn(mockOptional);

        Map<String, Object> dto = readJson("json/product/service/getEstimatedPremium_ok2_dto.json", Map.class);

        List<Long> warrantIds = ((ArrayList<Integer>) dto.get("warrantIds")).stream().map(Integer::longValue).collect(Collectors.toList());

        Integer productId = (Integer) dto.get("productId");

        Optional<EstimatedPremium> entityOptional = productService.getEstimatedPremium(productId.longValue(), warrantIds);

        then(productRepository).should().findById(anyLong(), eq(EstimatedPremium.class));

        EstimatedPremium entity = entityOptional.get();

        EstimatedPremium mock = mockOptional.get();

        assertEquals(entity.getProductTitle(), mock.getProductTitle());
        assertEquals(entity.getTerm(), mock.getTerm());
        assertEquals(entity.getPremium(), mock.getPremium());
    }
}
