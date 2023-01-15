package com.example.contract.repository;

import com.example.contract.doamin.Contract;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    @EntityGraph(attributePaths = {
            "product",
            "warrants"
    })
    <T> Optional<T> findById(Long id, Class<T> type);
}
