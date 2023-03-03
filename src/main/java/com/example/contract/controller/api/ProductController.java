package com.example.contract.controller.api;

import com.example.contract.controller.request.ProductSaveRequest;
import com.example.contract.dto.mapper.EstimatedPremium;
import com.example.contract.dto.model.product.EstimatedPremiumModel;
import com.example.contract.dto.model.product.ProductSaveModel;
import com.example.contract.dto.response.ProductResponse;
import com.example.contract.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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
        ProductSaveModel dto = new ProductSaveModel(req);
        return new ProductResponse(productService.created(dto));
    }

    /**
     * 총 보험료 API
     *
     * @param id         상품의 아이디
     * @param warrantIds 담보 데이터 (emptyable)
     * @return 총 보험료 데이터
     */
    @GetMapping("products/{id}/premium")
    public ResponseEntity<EstimatedPremium> selectEstimatedPremium(@PathVariable Long id, @RequestParam(value = "warrantIds", required = false) List<Long> warrantIds) {
        EstimatedPremiumModel dto = new EstimatedPremiumModel(id, warrantIds);
        Optional<EstimatedPremium> data = productService.getEstimatedPremium(dto);
        return data.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }
}
