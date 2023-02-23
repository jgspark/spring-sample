package com.example.contract.service;

import com.example.contract.config.exception.DataNotFoundException;
import com.example.contract.data.doamin.Product;
import com.example.contract.data.doamin.Warrant;
import com.example.contract.repository.ProductRepository;
import com.example.contract.repository.WarrantRepository;
import com.example.contract.data.projections.EstimatedPremium;
import com.example.contract.data.dto.ProductSaveRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 상품 서비스
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final WarrantRepository warrantRepository;

    /**
     * 상품 생성 메소드
     *
     * @param dto 상품 생성을 위한 데이터를 가지고 있습니다. {@link ProductSaveRequest}
     * @return 상품
     * @throws DataNotFoundException 담보 데이터가 없으면 발생이 됩니다.
     */
    @NotNull
    @Transactional
    public Product created(@NotNull ProductSaveRequest dto) {

        Set<Warrant> warrants = warrantRepository.findByIdIn(dto.getWarrantIds());

        if (warrants.isEmpty()) {
            throw new DataNotFoundException("Warrant Ids is " + Arrays.toString(dto.getWarrantIds().toArray()));
        }

        return productRepository.save(dto.toEntity(warrants));
    }

    /**
     * 예상 보험료를 계산하는 메소드 입니다.
     * <p>
     * 담보 데이터가 없다면, 전체 담보를 계산을 하게 됩니다.
     * <p>
     * 하지만 담보 데이터가 있다면 해당하는 담보를 조회를 합니다.
     *
     * @param id         상품 아이디
     * @param warrantIds 담보 데이터
     * @return 예상 보험료 데이터
     */
    @NotNull
    @Transactional(readOnly = true)
    public Optional<EstimatedPremium> getEstimatedPremium(@NotNull Long id, @NotNull List<Long> warrantIds) {

        if (warrantIds.isEmpty()) {
            return productRepository.findById(id, EstimatedPremium.class);
        }

        return productRepository.findByIdAndWarrants_IdIn(id, warrantIds, EstimatedPremium.class);
    }
}
