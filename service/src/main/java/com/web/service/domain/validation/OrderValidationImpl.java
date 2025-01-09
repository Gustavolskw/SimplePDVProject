package com.web.service.domain.validation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderValidationImpl implements OrderValidation {

    @Override
    public void validateTable(Integer tableNumber) {

    }

    @Override
    public void ValidateConsumerName(String consumerName) {

    }

    @Override
    public void validateGuide(Long guide) {

    }
}
