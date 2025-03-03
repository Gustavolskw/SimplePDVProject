package com.web.service.application.dto;

import java.util.List;

public record OrderUpdateDTO(String consumerName, Long guideId, Integer tableNumber, List<OrderProductDto> products) {
}
