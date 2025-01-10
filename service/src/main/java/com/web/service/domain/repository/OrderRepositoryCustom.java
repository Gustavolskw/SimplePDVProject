package com.web.service.domain.repository;

import com.web.service.presentation.viewModel.OrderSearchResult;

import java.util.List;

public interface OrderRepositoryCustom {
    List<OrderSearchResult> findByParams(String consumerName, String guideName, Integer tableNumber);
}
