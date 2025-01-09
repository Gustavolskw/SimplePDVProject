package com.web.service.domain.repository;

import com.web.service.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String productName);
    Optional<List<Product>> findAllByNameLike(String productName);

}
