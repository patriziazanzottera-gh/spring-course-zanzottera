package com.example.spring_course.service;

import com.example.spring_course.controller.model.OrderRequest;
import com.example.spring_course.controller.model.OrderResponse;
import com.example.spring_course.repository.CustomerRepository;
import com.example.spring_course.repository.OrderRepository;
import com.example.spring_course.repository.model.CustomerEntity;
import com.example.spring_course.repository.model.OrderEntity;
import com.example.spring_course.service.dto.OrderRequestDTO;
import com.example.spring_course.service.dto.OrderResponseDTO;
import com.example.spring_course.service.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final CustomerRepository customerRepository;
    private final OrderMapper mapper;

    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {

        OrderEntity orderEntity = mapper.dtoToEntity(orderRequestDTO);
        orderEntity = repository.save(orderEntity);
        return mapper.entityToDTO(orderEntity);
    }

    @CachePut(value="Order", key="#id")
    public OrderResponse update(
            Long id,
            OrderRequest request) {

        OrderEntity ordine = repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                NOT_FOUND,
                                "Ordine not found: " +
                                        id
                        )
                );
        request.setId(id);
        ordine = mapper.requestToEntity(request);
        if (request.getCustomer() != null) {
            Optional<CustomerEntity> customerEntity = customerRepository.findById(request.getCustomer().getId());
            if (customerEntity.isPresent()) {
                ordine.setCustomer(customerEntity.get());
            }
        }
        ordine = repository.save(ordine);
        return mapper.toResponse(ordine);
    }

    @CacheEvict(value="Ordine", key="#id")
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    NOT_FOUND,
                    "Product not found: " + id
            );
        }
        repository.deleteById(id);
    }

}