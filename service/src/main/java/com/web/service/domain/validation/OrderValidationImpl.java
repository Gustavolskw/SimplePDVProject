package com.web.service.domain.validation;

import com.web.service.domain.exception.EntityNotFoundException;
import com.web.service.domain.exception.ValidationException;
import com.web.service.domain.repository.OrderRepository;
import com.web.service.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderValidationImpl implements OrderValidation {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;


    @Override
    public void validateTable(Integer tableNumber) {
        if(tableNumber==null)throw new ValidationException("Número da mesa deve ser preenchido");
        if(tableNumber==0) throw new ValidationException("Número da mesa deve ser diferente de 0");
    }

    @Override
    public void ValidateConsumerName(String consumerName) {
        if(consumerName.isEmpty())throw new ValidationException("Consumer name deve ser preenchido");

    }

    @Override
    public void validateGuide(Long guide) {
        if(guide==null) throw new ValidationException("Guide deve ser preenchido");
        if(!userRepository.existsById(guide)) throw new EntityNotFoundException("Guia não encontrado!");
    }
}
