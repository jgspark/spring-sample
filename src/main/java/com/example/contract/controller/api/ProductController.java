package com.example.contract.controller.api;

import com.example.contract.dto.model.product.EstimatedPremiumModel;
import com.example.contract.dto.model.product.ProductSaveModel;
import com.example.contract.dto.request.ProductSaveRequest;
import com.example.contract.dto.response.EstimatedPremiumResponse;
import com.example.contract.dto.response.ProductResponse;
import com.example.contract.service.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 상품 컨트롤러
 */
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 상품 생성 API
     *
     * @param req 상품 생성에 필요한 데이터
     * @return 상품
     */
    @PostMapping("product")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse write(@RequestBody @Valid ProductSaveRequest req) {
        var dto = ProductSaveModel.of(req);
        return ProductResponse.of(productService.created(dto));
    }

    /**
     * 총 보험료 API
     *
     * @param id         상품의 아이디
     * @param warrantIds 담보 데이터 (emptyable)
     * @return 총 보험료 데이터
     */
    @GetMapping("products/{id}/premium")
    public ResponseEntity<EstimatedPremiumResponse> selectEstimatedPremium(@PathVariable Long id, @RequestParam(value = "warrantIds", required = false) List<Long> warrantIds) {
        var dto = EstimatedPremiumModel.of(id, warrantIds);
        var data = productService.getEstimatedPremium(dto);
        return data.map(m -> ResponseEntity.ok(EstimatedPremiumResponse.of(m))).orElseGet(() -> ResponseEntity.noContent().build());
    }
}
