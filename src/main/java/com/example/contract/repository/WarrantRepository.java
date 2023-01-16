package com.example.contract.repository;

import com.example.contract.doamin.Warrant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface WarrantRepository extends JpaRepository<Warrant, Long> {
    Set<Warrant> findByIdIn(Collection<Long> ids);
}
