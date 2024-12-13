package com.web.service.application.dto;

import java.math.BigDecimal;

public record ProductCreationDTO(String name, String description, BigDecimal value) {
}
