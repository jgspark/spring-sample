package com.example.contract.repository.jpa;

import com.example.contract.domain.entity.contract.Contract;
import com.example.contract.repository.ContractRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 계약 레파지토리
 */
public interface ContractJpaRepository extends JpaRepository<Contract, Long>, ContractRepository {

    @EntityGraph(attributePaths = {
            "product",
            "warrants"
    })
    <T> Optional<T> findById(Long id, Class<T> type);

    @Override
    @EntityGraph(attributePaths = {
            "product",
            "warrants"
    })
    Optional<Contract> findById(Long id);
}
