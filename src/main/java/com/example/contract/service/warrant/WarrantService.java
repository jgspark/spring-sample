package com.example.contract.service.warrant;

import com.example.contract.domain.entity.warrant.Warrant;
import com.example.contract.dto.model.warrant.WarrantSaveModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public interface WarrantService {


    Warrant created(@NotNull WarrantSaveModel dto);
}
