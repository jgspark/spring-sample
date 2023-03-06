package com.example.contract.service.product;

import com.example.contract.domain.product.Product;
import com.example.contract.dto.mapper.EstimatedPremium;
import com.example.contract.dto.model.product.EstimatedPremiumModel;
import com.example.contract.dto.model.product.ProductSaveModel;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface ProductService {

    Product created(@NotNull ProductSaveModel dto);

    Optional<EstimatedPremium> getEstimatedPremium(@NotNull EstimatedPremiumModel dto);
}
