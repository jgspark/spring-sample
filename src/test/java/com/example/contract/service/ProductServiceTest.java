package com.example.contract.service;

import com.example.contract.controller.request.ProductSaveRequest;
import com.example.contract.domain.product.Product;
import com.example.contract.domain.warrant.Warrant;
import com.example.contract.dto.mapper.EstimatedPremium;
import com.example.contract.dto.model.product.EstimatedPremiumModel;
import com.example.contract.dto.model.product.ProductSaveModel;
import com.example.contract.exception.AppException;
import com.example.contract.mock.EstimatedPremiumImpl;
import com.example.contract.repository.ProductRepository;
import com.example.contract.repository.WarrantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

@DisplayName("상품 서비스 레이어 에서")
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

    @Nested
    @DisplayName("저장 로직 은")
    class CreatedMethod {
        @Test
        @DisplayName("성공적으로 저장을 하게 된다.")
        public void created_ok() {

            Map map = readJson("json/product/service/created_ok.json", Map.class);

            Set<Warrant> warrantSet = convert(map.get("warrant"));

            Product mock = convert(convertProduct((Map) map.get("product")), warrantSet);

            given(warrantRepository.findByIdIn(any())).willReturn(warrantSet);

            given(productRepository.save(any())).willReturn(mock);

            ProductSaveRequest req = readJson("json/product/service/product_save_request.json", ProductSaveRequest.class);

            ProductSaveModel dto = new ProductSaveModel(req);

            Product entity = productService.created(dto);

            then(warrantRepository).should().findByIdIn(any());
            then(productRepository).should().save(any());

            assertEquals(entity.getTitle(), mock.getTitle());
            assertEquals(entity.getTerm(), mock.getTerm());
            assertEquals(entity.getWarrants(), mock.getWarrants());
        }

        @Test
        @DisplayName("담보 데이터가 없다면, AppException 이 발생이 된다.")
        public void created_fail1() {

            given(warrantRepository.findByIdIn(any())).willReturn(new HashSet<>());

            ProductSaveRequest req = readJson("json/product/service/product_save_request.json", ProductSaveRequest.class);

            ProductSaveModel dto = new ProductSaveModel(req);

            assertThrows(AppException.class, () -> productService.created(dto));
        }
    }

    @Nested
    @DisplayName("예상 총보험료 로직 은")
    class GetEstimatedPremiumMethod {

        @Test
        @DisplayName("담보 아이디들이 있을 때 정상적으로 조회가 된다.")
        public void getEstimatedPremium_ok1() {

            Optional<EstimatedPremium> mockOptional = Optional.of(
                    new EstimatedPremiumImpl(readJson("json/product/service/getEstimatedPremium_ok1.json", Product.class)));

            given(productRepository.findByIdAndWarrants_IdIn(anyLong(), any(), eq(EstimatedPremium.class))).willReturn(mockOptional);

            Map<String, Object> map = readJson("json/product/service/getEstimatedPremium_ok1_dto.json", Map.class);

            List<Long> warrantIds = ((ArrayList<Integer>) map.get("warrantIds")).stream().map(Integer::longValue).collect(
                    Collectors.toList());

            Integer productId = (Integer) map.get("productId");

            EstimatedPremiumModel dto = new EstimatedPremiumModel(productId.longValue(), warrantIds);

            Optional<EstimatedPremium> entityOptional = productService.getEstimatedPremium(dto);

            then(productRepository).should().findByIdAndWarrants_IdIn(anyLong(), any(), eq(EstimatedPremium.class));

            EstimatedPremium entity = entityOptional.get();

            EstimatedPremium mock = mockOptional.get();

            assertEquals(entity.getProductTitle(), mock.getProductTitle());
            assertEquals(entity.getTerm(), mock.getTerm());
            assertEquals(entity.getPremium(), mock.getPremium());
        }

        @Test
        @DisplayName("담보 아이디들이 없을 때 정상적으로 조회가 된다.")
        public void getEstimatedPremium_ok2() {

            Optional<EstimatedPremium> mockOptional = Optional.of(
                    new EstimatedPremiumImpl(readJson("json/product/service/getEstimatedPremium_ok2.json", Product.class)));

            given(productRepository.findById(anyLong(), eq(EstimatedPremium.class))).willReturn(mockOptional);

            Map<String, Object> map = readJson("json/product/service/getEstimatedPremium_ok2_dto.json", Map.class);

            List<Long> warrantIds = ((ArrayList<Integer>) map.get("warrantIds")).stream().map(Integer::longValue).collect(
                    Collectors.toList());

            Integer productId = (Integer) map.get("productId");

            EstimatedPremiumModel dto = new EstimatedPremiumModel(productId.longValue(), warrantIds);

            Optional<EstimatedPremium> entityOptional = productService.getEstimatedPremium(dto);

            then(productRepository).should().findById(anyLong(), eq(EstimatedPremium.class));

            EstimatedPremium entity = entityOptional.get();

            EstimatedPremium mock = mockOptional.get();

            assertEquals(entity.getProductTitle(), mock.getProductTitle());
            assertEquals(entity.getTerm(), mock.getTerm());
            assertEquals(entity.getPremium(), mock.getPremium());
        }
    }

}
