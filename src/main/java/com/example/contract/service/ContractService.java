package com.example.contract.service;

import com.example.contract.doamin.Contract;
import com.example.contract.doamin.Product;
import com.example.contract.repository.ContractRepository;
import com.example.contract.repository.ProductRepository;
import com.example.contract.web.dto.ContractSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;

    private final ProductRepository productRepository;

    @Transactional
    public Contract created(ContractSaveRequest dto) {

        // todo : exception
        Product product = productRepository.findByIdAndWarrants_IdIn(dto.getProductId(), dto.getWarrantIds()).orElseThrow(() -> new RuntimeException("not found"));

        BigDecimal premium = product.calculatePremium();

        Contract entity = dto.toEntity(product, product.getWarrants(), premium);

        return contractRepository.save(entity);
    }
}
