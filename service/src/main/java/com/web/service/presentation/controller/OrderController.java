package com.web.service.presentation.controller;

import com.web.service.application.dto.OrderPlacingDTO;
import com.web.service.application.dto.OrderProductDto;
import com.web.service.application.service.OrderService;
import com.web.service.presentation.viewModel.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse> placeOrder(@Valid @RequestBody OrderPlacingDTO orderDto) {
            orderService.placeOrder(orderDto);
            return ResponseEntity.ok().body(new ApiResponse("Pedido adicionado com sucesso!", null));
    }

    @PostMapping("/{id}/include")
    public ResponseEntity<ApiResponse> includePorduct( @PathVariable Long id, @Valid @RequestBody OrderProductDto orderProductDto){
        orderService.includeProductOnOrder(id, orderProductDto);
        return ResponseEntity.ok().body(new ApiResponse("Produto adicionado ao pedido com sucesso!", null));
    }
}