package com.web.service.domain.validation;

import com.web.service.domain.exception.AlreadyExistsException;
import com.web.service.domain.exception.ValidationException;
import com.web.service.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductValidateImpl implements ProductValidation {
    private final ProductRepository productRepository;

    @Override
    public void validateName(String name) {
        if(name.isEmpty())throw new ValidationException("Product name is required");
        if(productRepository.findByName(name).isPresent())throw new AlreadyExistsException("Product already exists");
        if(name.length() < 4)throw new ValidationException("Product name need to be longer");
    }

    @Override
    public void validatePrice(BigDecimal price) {
        if(price == null)throw new ValidationException("Product price is required");
        if(price.compareTo(BigDecimal.ZERO) <= 0)throw new ValidationException("Product price need to be positive");
    }


    @Override
    public void validateDescription(String description) {
    if(description.isEmpty())throw new ValidationException("Product description is required");
    if (description.length() < 5)throw new ValidationException("Product description need to be longer");
    }
}
