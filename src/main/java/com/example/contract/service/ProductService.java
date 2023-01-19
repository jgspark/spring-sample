package com.example.contract.service;

import com.example.contract.config.exception.DataNotFoundException;
import com.example.contract.doamin.Product;
import com.example.contract.doamin.Warrant;
import com.example.contract.repository.ProductRepository;
import com.example.contract.repository.WarrantRepository;
import com.example.contract.web.dto.EstimatedPremium;
import com.example.contract.web.dto.ProductSaveRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final WarrantRepository warrantRepository;

    @NotNull
    @Transactional
    public Product created(@NotNull ProductSaveRequest dto) {

        Set<Warrant> warrants = warrantRepository.findByIdIn(dto.getWarrantIds());

        if (warrants.isEmpty()) {
            throw new DataNotFoundException("Warrant Ids is " + Arrays.toString(dto.getWarrantIds().toArray()));
        }

        return productRepository.save(dto.toEntity(warrants));
    }

    @NotNull
    @Transactional(readOnly = true)
    public Optional<EstimatedPremium> getEstimatedPremium(@NotNull Long id, @NotNull List<Long> warrantIds) {

        if (warrantIds.isEmpty()) {
            return productRepository.findById(id, EstimatedPremium.class);
        }

        return productRepository.findByIdAndWarrants_IdIn(id, warrantIds, EstimatedPremium.class);
    }
}
