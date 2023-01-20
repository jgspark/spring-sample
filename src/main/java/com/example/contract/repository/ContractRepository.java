package com.example.contract.repository;

import com.example.contract.doamin.Contract;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *  거래 레파지토리
 */
@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

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
