package com.example.contract.repository;

import com.example.contract.domain.entity.warrant.Warrant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

/**
 * 담보 레파지토리
 */
@Repository
public interface WarrantRepository extends JpaRepository<Warrant, Long> {
    Set<Warrant> findByIdIn(Collection<Long> ids);
}
