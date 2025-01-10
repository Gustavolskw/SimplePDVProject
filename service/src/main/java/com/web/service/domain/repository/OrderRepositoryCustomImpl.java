package com.web.service.domain.repository;

import com.web.service.presentation.viewModel.OrderSearchResult;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<OrderSearchResult> findByParams(String consumerName, String guideName, Integer tableNumber) {
        String sql = "SELECT o.id AS id, o.consumer_name AS consumerName, o.table_number AS tableNumber, " +
                "u.username AS guideName, o.status AS status, o.created_at AS createdAt, o.updated_at AS updatedAt " +
                "FROM CARDAPIO_LOJA.orders o " +
                "INNER JOIN CARDAPIO_LOJA.users u ON o.guide = u.id " +
                "WHERE (:consumerName IS NULL OR o.consumer_name LIKE CONCAT('%', :consumerName, '%')) " +
                "AND (:guideName IS NULL OR u.username LIKE CONCAT('%', :guideName, '%')) " +
                "AND (:tableNumber IS NULL OR o.table_number = :tableNumber)";

        Query query = entityManager.createNativeQuery(sql, "OrderSearchResultMapping");
        query.setParameter("consumerName", consumerName);
        query.setParameter("guideName", guideName);
        query.setParameter("tableNumber", tableNumber);

        return query.getResultList();
    }
}
