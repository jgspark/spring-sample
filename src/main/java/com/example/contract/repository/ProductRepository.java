package com.example.contract.repository;

import com.example.contract.domain.entity.product.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * 상품 레파지토리
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = {
            "warrants"
    })
    Optional<Product> findByIdAndWarrants_IdIn(Long id, Collection<Long> warrantIds);

    @EntityGraph(attributePaths = {
            "warrants"
    })
    <T> Optional<T> findById(Long id, Class<T> type);

    @EntityGraph(attributePaths = {
            "warrants"
    })
    <T> Optional<T> findByIdAndWarrants_IdIn(Long id, Collection<Long> warrantIds, Class<T> type);
}
