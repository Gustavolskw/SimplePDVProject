package com.web.service.application.service;

import com.web.service.application.dto.OrderPlacingDTO;
import com.web.service.domain.model.Order;
import com.web.service.domain.model.User;
import com.web.service.domain.repository.OrderRepository;
import com.web.service.domain.validation.OrderValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderValidation orderValidation;


    public void placeOrder(OrderPlacingDTO orderPlacingDTO) {
        orderValidation.validateTable(orderPlacingDTO.tableNum());
        orderValidation.ValidateConsumerName(orderPlacingDTO.consumerName());
        orderValidation.validateGuide(orderPlacingDTO.guide());
//        User guide =
//                Order newOrder = buildOrder(orderPlacingDTO);
    }

    public Order buildOrder(OrderPlacingDTO orderDto, User guide) {
        Order order = new Order();
        order.setConsumerName(orderDto.consumerName());
        order.setTableNumber(orderDto.tableNum());
        order.setGuide(guide);
        order.setStatus(true);
        return order;
    }

}
