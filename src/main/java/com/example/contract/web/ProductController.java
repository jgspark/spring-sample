package com.example.contract.web;

import com.example.contract.service.ProductService;
import com.example.contract.web.dto.EstimatedPremium;
import com.example.contract.web.dto.ProductResponse;
import com.example.contract.web.dto.ProductSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("product")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@RequestBody ProductSaveRequest dto) {
        return new ProductResponse(productService.created(dto));
    }

    @GetMapping("products/{id}/premium")
    public ResponseEntity<EstimatedPremium> selectEstimatedPremium(@PathVariable Long id, @RequestParam("warrantIds") List<Long> warrantIds) {
        Optional<EstimatedPremium> data = productService.getEstimatedPremium(id, warrantIds);
        return data.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }
}
