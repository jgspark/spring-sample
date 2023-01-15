package com.example.contract.service;

import com.example.contract.repository.ProductRepository;
import com.example.contract.web.dto.EstimatedPremium;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PremiumService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Optional<EstimatedPremium> getEstimatedPremium(Long id) {
        return productRepository.findById(id, EstimatedPremium.class);
    }
}
