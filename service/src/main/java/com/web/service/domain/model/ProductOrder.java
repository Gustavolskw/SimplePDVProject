package com.web.service.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

//@Entity
//@Table(name = "PRODUCT_ORDER")
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
@Data
public class ProductOrder {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "order_id")
    private Order order;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id")
    private Product product;
    private Integer quantity;
}