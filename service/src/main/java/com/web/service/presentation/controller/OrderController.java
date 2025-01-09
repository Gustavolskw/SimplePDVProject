package com.web.service.presentation.controller;

import com.web.service.application.dto.OrderPlacingDTO;
import com.web.service.application.service.OrderService;
import com.web.service.presentation.viewModel.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse> placeOrder(@Valid @RequestBody OrderPlacingDTO orderDto) {
            return ResponseEntity.ok().body(new ApiResponse("Pedido adicionado com sucesso!", null));
    }
}
