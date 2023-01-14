package com.example.contract.repository;

import com.example.contract.doamin.Warrant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarrantRepository extends JpaRepository<Warrant, Long> {
}
