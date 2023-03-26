package com.example.contract.service.contract;

import com.example.contract.aop.IOLogger;
import com.example.contract.domain.entity.contract.Contract;
import com.example.contract.domain.entity.product.Product;
import com.example.contract.domain.mapper.ContractDetail;
import com.example.contract.dto.model.contract.ContractSaveModel;
import com.example.contract.dto.model.contract.ContractUpdateModel;
import com.example.contract.dto.request.ContractSaveRequest;
import com.example.contract.dto.request.ContractUpdateRequest;
import com.example.contract.exception.AppException;
import com.example.contract.exception.DataNotFoundException;
import com.example.contract.repository.ContractRepository;
import com.example.contract.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * 계약 서비스
 */
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

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
    @IOLogger
    @Transactional
    public Contract created(ContractSaveModel dto) {

        // 조회를 한다.
        Product product = productRepository.findByIdAndWarrants_IdIn(dto.productId(), dto.warrantIds())
                .orElseThrow(() -> new DataNotFoundException("Product Id is " + dto.productId()));

        // 조회를 해서 계산을 한다.
        BigDecimal premium = product.calculatePremium();

        Contract entity = contractRepository.save(dto.toEntity(product, premium));

        entity.addWarrant(product);

        return entity;
    }

    /**
     * 계약 상세 조회
     *
     * @param id 계약 아이디
     * @return 계약 상세 데이터를 반환 해줍니다.
     */
    @NotNull
    @IOLogger
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
    @IOLogger
    @Transactional
    public Contract update(@NotNull ContractUpdateModel dto) {

        Long id = dto.id();

        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Contract Id is " + id));

        if (contract.isExpiration()) {
            throw new AppException("contract is state (Expiration) and id is " + id);
        }

        Long productId = contract.getProduct().getId();

        Product product = productRepository.findByIdAndWarrants_IdIn(productId, dto.warrantIds())
                .orElseThrow(() -> new DataNotFoundException("Product Id is " + productId));

        BigDecimal premium = product.calculatePremium();

        contract.update(product.getWarrants(), dto.term(), dto.state(), premium);

        return contract;
    }
}
