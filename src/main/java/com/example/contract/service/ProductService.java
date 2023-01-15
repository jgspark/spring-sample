package com.example.contract.service;

import com.example.contract.repository.ProductRepository;
import com.example.contract.web.dto.EstimatedPremium;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Optional<EstimatedPremium> getEstimatedPremium(Long id, List<Long> warrantIds) {

        if (warrantIds.isEmpty()) {
            return productRepository.findById(id, EstimatedPremium.class);
        }

        return productRepository.findByIdAndWarrants_IdIn(id, warrantIds, EstimatedPremium.class);
    }
}
