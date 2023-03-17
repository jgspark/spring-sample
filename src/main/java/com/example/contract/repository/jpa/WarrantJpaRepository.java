package com.example.contract.repository.jpa;

import com.example.contract.domain.entity.warrant.Warrant;
import com.example.contract.repository.WarrantRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Set;

/**
 * 담보 레파지토리
 */
public interface WarrantJpaRepository extends JpaRepository<Warrant, Long> , WarrantRepository {
    Set<Warrant> findByIdIn(Collection<Long> ids);
}
