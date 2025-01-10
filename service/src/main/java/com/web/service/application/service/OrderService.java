package com.web.service.application.service;

import com.web.service.application.dto.OrderPlacingDTO;
import com.web.service.application.dto.OrderProductDto;
import com.web.service.domain.exception.EntityNotFoundException;
import com.web.service.domain.model.Order;
import com.web.service.domain.model.Product;
import com.web.service.domain.model.ProductOrder;
import com.web.service.domain.model.User;
import com.web.service.domain.repository.OrderRepository;
import com.web.service.domain.validation.OrderValidation;
import com.web.service.presentation.viewModel.OrderResponse;
import com.web.service.presentation.viewModel.OrderSearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderValidation orderValidation;
    private final UserService userService;
    private final ProductOrderService productOrderService;


    public void placeOrder(OrderPlacingDTO orderPlacingDTO) {
        orderValidation.validateTable(orderPlacingDTO.tableNum());
        orderValidation.ValidateConsumerName(orderPlacingDTO.consumerName());
        orderValidation.validateGuide(orderPlacingDTO.guide());
        User guide = userService.findById(orderPlacingDTO.guide());
        Order newOrder = buildOrder(orderPlacingDTO, guide);
        orderRepository.save(newOrder);
    }

    public void includeProductOnOrder(Long id, OrderProductDto orderProductDto) {
        orderValidation.validateOrder(id);
        orderValidation.validateProductOnOrder(orderProductDto.productId(), id);
        orderValidation.validateInsertProductOnOrder(orderProductDto.productId(), id);
        orderValidation.validateQuantityProductOrder(orderProductDto.quantity());
        productOrderService.includeProduct(findById(id), orderProductDto);
    }

    public void decreaseProductOnOrder(Long id, OrderProductDto orderProductDto) {
        orderValidation.validateOrder(id);
        orderValidation.validateProductOnOrder(orderProductDto.productId(), id);
        productOrderService.decrementProduct(id, orderProductDto.productId());
    }

    public void increaseProductOnOrder(Long id, OrderProductDto orderProductDto) {
        orderValidation.validateOrder(id);
        orderValidation.validateProductOnOrder(orderProductDto.productId(), id);
        productOrderService.increaseQuantityOnOrder(id, orderProductDto.productId());
    }

    public void adjustProductOnOrder(Long id, OrderProductDto orderProductDto) {
        orderValidation.validateOrder(id);
        orderValidation.validateProductOnOrder(orderProductDto.productId(), id);
        orderValidation.validateQuantityProductOrder(orderProductDto.quantity());
        productOrderService.adjustQuantityOnOrder(id, orderProductDto.productId(), orderProductDto.quantity());
    }

    public void excludeProductOnOrder(Long id, Long productId) {
        orderValidation.validateOrder(id);
        orderValidation.validateProductOnOrder(productId, id);
        productOrderService.excludeProduct(id, productId);
    }

    public Order findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if(order.isEmpty())throw new EntityNotFoundException("Order not found");
        return order.get();
    }

    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public Order buildOrder(OrderPlacingDTO orderDto, User guide) {
        Order order = new Order();
        order.setConsumerName(orderDto.consumerName());
        order.setTableNumber(orderDto.tableNum());
        order.setGuide(guide);
        order.setStatus(true);
        return order;
    }

    public List<OrderSearchResult> getOrdersByParam(String guide, String consumer, Integer table) {
        return orderRepository.findByParams(consumer, guide, table);
    }



}
