package com.example.contract.repository;

import com.example.contract.domain.entity.warrant.Warrant;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Repository
public interface WarrantRepository {
    Set<Warrant> findByIdIn(Collection<Long> warrantIds);

    Warrant save(Warrant toEntity);

    Optional<Warrant> findById(Long id);
}
