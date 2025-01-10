package com.web.service.presentation.viewModel;

import com.web.service.domain.model.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponse(Long orderId,
                            Integer tableNumber,
                            String consumerName,
                            String guideName,
                            Boolean status,
                            BigDecimal totalValue,
                            Integer productsInOrder,
                            LocalDateTime createdAt,
                            LocalDateTime updatedAt) {
    public OrderResponse(Order o){
        this(o.getId(),
                o.getTableNumber(),
                o.getConsumerName(),
                o.getGuide().getUsername(),
                o.getStatus(),
                o.getProductsOnOrder().stream()
                        .map(
                        productOrder ->
                                productOrder.getProduct()
                                        .getValue()
                                        .multiply(BigDecimal.valueOf(productOrder.getQuantity()))
                ).reduce(BigDecimal.ZERO, BigDecimal::add),
                o.getProductsOnOrder().size(),
                o.getCreatedAt(),
                o.getUpdatedAt()
        );
    }

}
