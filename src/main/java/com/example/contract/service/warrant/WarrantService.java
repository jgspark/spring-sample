package com.example.contract.service.warrant;

import com.example.contract.domain.warrant.Warrant;
import com.example.contract.dto.model.warrant.WarrantSaveModel;
import org.jetbrains.annotations.NotNull;

public interface WarrantService {


    Warrant created(@NotNull WarrantSaveModel dto);
}
