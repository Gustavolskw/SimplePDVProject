package com.web.service.application.service;

import com.web.service.application.dto.OrderPlacingDTO;
import com.web.service.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductService productService;
    private final OrderRepository orderRepository;


    public void placeOrder(OrderPlacingDTO orderPlacingDTO) {

    }


}
