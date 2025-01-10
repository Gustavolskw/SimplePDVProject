package com.web.service.presentation.viewModel;

import com.web.service.domain.model.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record CompleteOrderResponse(
        Long id,
        String consumerName,
        Integer tableId,
        String guide,
        Boolean status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        BigDecimal totalPrice,
        Set<ProductOrderResponse> products
        ) {
    public CompleteOrderResponse(Order order){
        this(order.getId(),
                order.getConsumerName(),
                order.getTableNumber(),
                order.getGuide().getUsername(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getUpdatedAt(),
                order.getProductsOnOrder().stream()
                        .map(productOrder -> productOrder.getProduct().getValue()
                                .multiply(BigDecimal.valueOf(productOrder.getQuantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add), // Calculate total price
                order.getProductsOnOrder().stream().map(ProductOrderResponse::new).collect(Collectors.toSet())
        );
    }
}
