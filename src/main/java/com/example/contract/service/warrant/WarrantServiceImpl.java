package com.example.contract.service.warrant;

import com.example.contract.aop.IOLogger;
import com.example.contract.controller.request.WarrantSaveRequest;
import com.example.contract.domain.entity.warrant.Warrant;
import com.example.contract.dto.model.warrant.WarrantSaveModel;
import com.example.contract.repository.WarrantRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 담보 서비스
 */
@Service
@RequiredArgsConstructor
public class WarrantServiceImpl implements WarrantService {

    private final WarrantRepository warrantRepository;

    /**
     * 담보를 생성하는 메소드 입니다.
     *
     * @param dto 담보 생성에 필요한 데이터를 가지로 있습니다 {@link WarrantSaveRequest}
     * @return 담보
     */
    @IOLogger
    @Transactional
    public Warrant created(@NotNull WarrantSaveModel dto) {
        return warrantRepository.save(dto.toEntity());
    }
}
