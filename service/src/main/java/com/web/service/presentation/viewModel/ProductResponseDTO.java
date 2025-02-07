package com.web.service.presentation.viewModel;

import com.web.service.domain.model.Product;

import java.math.BigDecimal;

public record ProductResponseDTO(Long id, String name, String description, String type, Boolean status,  BigDecimal value,  String imageUrl) {
    public ProductResponseDTO(Product p){
        this(p.getId(), p.getName(), p.getDescription(),p.getProductType().getName(), p.getStatus(), p.getValue(), p.getImageUrl());
    }
}
