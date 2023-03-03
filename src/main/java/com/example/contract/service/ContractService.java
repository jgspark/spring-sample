package com.example.contract.service;

import com.example.contract.dto.model.contract.ContractSaveModel;
import com.example.contract.dto.model.contract.ContractUpdateModel;
import com.example.contract.exception.AppException;
import com.example.contract.exception.DataNotFoundException;
import com.example.contract.domain.contract.Contract;
import com.example.contract.domain.product.Product;
import com.example.contract.repository.ContractRepository;
import com.example.contract.repository.ProductRepository;
import com.example.contract.dto.mapper.ContractDetail;
import com.example.contract.controller.request.ContractSaveRequest;
import com.example.contract.controller.request.ContractUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * 계약 서비스
 */
@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;

    private final ProductRepository productRepository;

    /**
     * 계약 생성 메소드
     *
     * @param dto 계약 생성에 필요한 데이터를 가지고 있습니다. {@link ContractSaveRequest}
     * @return 계약 데이터
     * @throws DataNotFoundException 상품 데이터가 없으면 예외
     */
    @NotNull
    @Transactional
    public Contract created(ContractSaveModel dto) {

        Product product = productRepository.findByIdAndWarrants_IdIn(dto.getProductId(), dto.getWarrantIds())
                .orElseThrow(() -> new DataNotFoundException("Product Id is " + dto.getProductId()));

        BigDecimal premium = product.calculatePremium();

        Contract entity = contractRepository.save(dto.toEntity(product, premium));

        entity.getWarrants().addAll(product.getWarrants());

        return entity;
    }

    /**
     * 계약 상세 조회
     *
     * @param id 계약 아이디
     * @return 계약 상세 데이터를 반환 해줍니다.
     */
    @NotNull
    @Transactional(readOnly = true)
    public Optional<ContractDetail> getOne(@NotNull Long id) {
        return contractRepository.findById(id, ContractDetail.class);
    }

    /**
     * 계약 수정 메소드
     *
     * @param dto 계약 변경 데이터를 담고 있습니다. {@link ContractUpdateRequest}
     * @return 수정된  계약 데이터
     * @throws AppException          기간 만료 상태라면 업데이터 할 수 없기에 예외
     * @throws DataNotFoundException 계약 데이터 과 상품데이터  없으면 예외
     */
    @NotNull
    @Transactional
    public Contract update(@NotNull ContractUpdateModel dto) {

        Long id = dto.getId();

        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Contract Id is " + id));

        if (contract.isExpiration()) {
            throw new AppException("contract is state (Expiration) and id is " + id);
        }

        Long productId = contract.getProduct().getId();

        Product product = productRepository.findByIdAndWarrants_IdIn(productId, dto.getWarrantIds())
                .orElseThrow(() -> new DataNotFoundException("Product Id is " + productId));

        BigDecimal premium = product.calculatePremium();

        contract.update(product.getWarrants(), dto.getTerm(), dto.getState(), premium);

        return contract;
    }
}
