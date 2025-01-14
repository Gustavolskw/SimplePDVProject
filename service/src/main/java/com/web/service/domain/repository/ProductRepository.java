package com.web.service.domain.repository;

import com.web.service.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String productName);
    Optional<List<Product>> findAllByNameLike(String productName);


    @Query(nativeQuery = true, value = "SELECT status from products p where id=:productId")
    Integer isValid(@Param(value = "productId") Long productId);


    @Query(nativeQuery = true, value = "SELECT * FROM CARDAPIO_LOJA.products p WHERE p.name LIKE :prodName")
    Optional<List<Product>> searchProductsByParamName(@Param("prodName") String prodName);

    boolean existsByProductTypeId(Long id);
}
