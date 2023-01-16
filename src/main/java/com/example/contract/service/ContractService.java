package com.example.contract.service;

import com.example.contract.config.exception.DataNotFoundException;
import com.example.contract.doamin.Contract;
import com.example.contract.doamin.Product;
import com.example.contract.repository.ContractRepository;
import com.example.contract.repository.ProductRepository;
import com.example.contract.web.dto.ContractDetail;
import com.example.contract.web.dto.ContractSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;

    private final ProductRepository productRepository;

    @Transactional
    public Contract created(ContractSaveRequest dto) {

        Product product = productRepository.findByIdAndWarrants_IdIn(dto.getProductId(), dto.getWarrantIds()).orElseThrow(() -> new DataNotFoundException("Product Id is " + dto.getProductId()));

        BigDecimal premium = product.calculatePremium();

        Contract entity = contractRepository.save(dto.toEntity(product, premium));

        entity.getWarrants().addAll(product.getWarrants());

        return entity;
    }

    @Transactional(readOnly = true)
    public Optional<ContractDetail> getOne(Long id) {
        return contractRepository.findById(id, ContractDetail.class);
    }
}
