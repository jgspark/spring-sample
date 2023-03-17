package com.example.contract.repository;

import com.example.contract.domain.entity.product.Product;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface ProductRepository {

    Optional<Product> findByIdAndWarrants_IdIn(Long id, Collection<Long> warrantIds);

    <T> Optional<T> findByIdAndWarrants_IdIn(Long productId, Collection<Long> warrantIds, Class<T> type);

    Product save(Product mock);

    <T> Optional<T> findById(Long id, Class<T> type);
}
