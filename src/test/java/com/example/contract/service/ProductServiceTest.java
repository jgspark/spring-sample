package com.example.contract.service;

import com.example.contract.config.exception.AppException;
import com.example.contract.doamin.Product;
import com.example.contract.doamin.Warrant;
import com.example.contract.repository.ProductRepository;
import com.example.contract.repository.WarrantRepository;
import com.example.contract.web.dto.ProductSaveRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.example.contract.mock.MockUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

        assertEquals(mock.getTitle(), entity.getTitle());
        assertEquals(mock.getTerm(), entity.getTerm());
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
}
