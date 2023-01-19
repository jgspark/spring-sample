package com.example.contract.service;

import com.example.contract.doamin.Warrant;
import com.example.contract.repository.WarrantRepository;
import com.example.contract.web.dto.WarrantSaveRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 담보 서비스
 */
@Service
@RequiredArgsConstructor
public class WarrantService {

    private final WarrantRepository warrantRepository;

    /**
     * 담보를 생성하는 메소드 입니다.
     *
     * @param dto 담보 생성에 필요한 데이터를 가지로 있습니다 {@link WarrantSaveRequest}
     * @return 담보
     */
    @Transactional
    public @NotNull Warrant created(@NotNull WarrantSaveRequest dto) {
        return warrantRepository.save(dto.toEntity());
    }
}
