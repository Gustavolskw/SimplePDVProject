package com.web.service.domain.validation;

public interface OrderValidation {
    void validateTable(Integer tableNumber);
    void ValidateConsumerName(String consumerName);
    void validateGuide(Long guide);
}
