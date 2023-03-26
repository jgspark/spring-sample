package com.example.contract.service.contract;

import com.example.contract.domain.entity.contract.Contract;
import com.example.contract.domain.mapper.ContractDetail;
import com.example.contract.dto.model.contract.ContractSaveModel;
import com.example.contract.dto.model.contract.ContractUpdateModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface ContractService {

    Contract created(ContractSaveModel dto);

    Optional<ContractDetail> getOne(@NotNull Long id);

    Contract update(@NotNull ContractUpdateModel dto);

}
