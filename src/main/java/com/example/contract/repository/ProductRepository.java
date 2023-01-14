package com.example.contract.repository;

import com.example.contract.doamin.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByIdAndWarrants_IdIn(Long id, Collection<Long> warrants_id);
}
