package com.web.service.domain.validation;

public interface OrderValidation {
    void validateOrder(Long id);
    void validateTable(Integer tableNumber);
    void ValidateConsumerName(String consumerName);
    void validateGuide(Long guide);
    void validateProductOnOrder(Long productId, Long id);
    void validateQuantityProductOrder(Integer quantityProductOrder);
}
