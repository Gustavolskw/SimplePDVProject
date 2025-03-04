package com.web.service.domain.repository;

import com.web.service.domain.model.ProductOrder;
import com.web.service.domain.model.ProductOrderId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
    Optional<ProductOrder> findByOrderIdAndProductId(Long orderId, Long productId);
    @Modifying
    @Query("DELETE FROM ProductOrder po WHERE po.order.id = :orderId AND po.product.id IN :productIds")
    void deleteByOrderIdAndProductIdIn(@Param("orderId") Long orderId, @Param("productIds") List<Long> productIds);

    void deleteById_OrderId(Long orderId);
}
