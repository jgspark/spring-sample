package com.example.contract.repository;

import com.example.contract.domain.entity.contract.Contract;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractRepository {

    Optional<Contract> findById(Long id);

    <T> Optional<T> findById(Long id, Class<T> type);

    Contract save(Contract toEntity);
}
