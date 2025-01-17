package com.web.service.presentation.controller;

import com.web.service.application.dto.OrderPlacingDTO;
import com.web.service.application.dto.OrderProductDto;
import com.web.service.application.service.OrderService;
import com.web.service.domain.model.Order;
import com.web.service.presentation.viewModel.ApiResponse;
import com.web.service.presentation.viewModel.CompleteOrderResponse;
import com.web.service.presentation.viewModel.OrderResponse;
import com.web.service.presentation.viewModel.PageableResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @PostMapping("/{id}/decrease")
    public ResponseEntity<ApiResponse> decreaseItemOrder(@PathVariable Long id, @Valid @RequestBody OrderProductDto orderProductDto){
        orderService.decreaseProductOnOrder(id, orderProductDto);
        return ResponseEntity.ok().body(new ApiResponse("Produto reduzido com sucesso!", null));
    }

    @PostMapping("/{id}/increase")
    public ResponseEntity<ApiResponse> increaseItemOrder(@PathVariable Long id, @Valid @RequestBody OrderProductDto orderProductDto){
        orderService.increaseProductOnOrder(id, orderProductDto);
        return ResponseEntity.ok().body(new ApiResponse("Quantidade de produto adicionado ao pedido com sucesso!", null));
    }

    @PutMapping("/{id}/ajust")
    public ResponseEntity<ApiResponse> adjustItemOrder(@PathVariable Long id, @Valid @RequestBody OrderProductDto orderProductDto){
        orderService.adjustProductOnOrder(id, orderProductDto);
        return ResponseEntity.ok().body(new ApiResponse("Quantidade de produto ajustado com sucesso!", null));
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<ApiResponse> closeOrder(@PathVariable Long id){
        orderService.closeOrder(id);
        return ResponseEntity.ok().body(new ApiResponse("Ordem  de Pedido finalizado com sucesso!", null));
    }

    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse> cancelOrder(@PathVariable Long id){
        orderService.cancelOrder(id);
        return ResponseEntity.ok().body(new ApiResponse("Ordem Cancelada com sucesso!", null));
    }

    @DeleteMapping("/{id}/remove/{prodtId}")
    public ResponseEntity<ApiResponse> removeItem(@PathVariable Long id, @PathVariable Long prodtId){
        orderService.excludeProductOnOrder(id, prodtId);
        return ResponseEntity.ok().body(new ApiResponse("Produto excluido do pedido com sucesso!", null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllOrders(@PageableDefault(size = 20) Pageable pageable){
        Page<Order> responsePaged = orderService.getAllOrders(pageable);

        // Transform Order to OrderResponse (or any other DTO)
        Page<OrderResponse> responseTransformed = responsePaged.map(OrderResponse::new);

        return ResponseEntity.ok().body(new ApiResponse(
                "Lista de Ordens",
                new PageableResponse<>(responseTransformed)
        ));

    }


    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchOrders(@PageableDefault(size = 20) Pageable pageable,
                                                    @RequestParam(name = "consumer", required = false) String consumerName,
                                                    @RequestParam(name = "guide", required = false) String guideName,
                                                    @RequestParam(name = "table", required = false)Integer tableNumber){

        return ResponseEntity.ok().body(new ApiResponse("Ordem de pedido", orderService.getOrdersByParam(guideName,consumerName, tableNumber)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long id){
        return ResponseEntity.ok().body(new ApiResponse("Pedido encontrado!", new CompleteOrderResponse(orderService.findById(id))));
    }

}