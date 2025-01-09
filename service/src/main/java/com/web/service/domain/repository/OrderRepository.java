package com.web.service.domain.repository;

import com.web.service.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {


    @Query(nativeQuery = true, value = "SELECT COUNT(id) AS quantidade FROM orders WHERE table_number = :tableNumber")
    public Integer countTableAmountUsed(@Param(value = "tableNumber") Integer tableNumber);
}
