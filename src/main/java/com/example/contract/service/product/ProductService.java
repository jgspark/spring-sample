package com.example.contract.service.product;

import com.example.contract.domain.entity.product.Product;
import com.example.contract.domain.mapper.EstimatedPremium;
import com.example.contract.dto.model.product.EstimatedPremiumModel;
import com.example.contract.dto.model.product.ProductSaveModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ProductService {

    Product created(@NotNull ProductSaveModel dto);

    Optional<EstimatedPremium> getEstimatedPremium(@NotNull EstimatedPremiumModel dto);
}
