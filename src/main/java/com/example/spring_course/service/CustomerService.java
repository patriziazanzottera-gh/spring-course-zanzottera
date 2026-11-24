package com.example.spring_course.service;

import com.example.spring_course.controller.model.CustomerResponse;
import com.example.spring_course.exception.AppException;
import com.example.spring_course.repository.CustomerRepository;
import com.example.spring_course.repository.OrderRepository;
import com.example.spring_course.repository.model.CustomerEntity;
import com.example.spring_course.repository.model.OrderEntity;
import com.example.spring_course.service.dto.CustomerRequestDTO;
import com.example.spring_course.service.dto.CustomerResponseDTO;
import com.example.spring_course.service.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;


@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final OrderRepository orderRepository;
    private final CustomerMapper mapper;

    public CustomerResponseDTO createCustomer(CustomerRequestDTO customerRequestDTO) {
        repository.findByEmail(customerRequestDTO.getEmail()).ifPresent(customer -> {
            log.error("Customer già esistente {}",customerRequestDTO.getEmail());
            throw new AppException("Customer " + customerRequestDTO.getEmail() + " già presente",
                    HttpStatus.CONFLICT
            );
        });

        CustomerEntity customerEntity = new CustomerEntity();

        if (customerRequestDTO.getOrders() != null && !customerRequestDTO.getOrders().isEmpty()) {
            List<OrderEntity> orders = customerRequestDTO.getOrders().stream()
                    .map(orderRequest ->
                            orderRepository.findById(orderRequest.getId())
                                    .orElseThrow(() ->
                                            new RuntimeException("Order not found")
                                    )
                    )
                    .toList();
            customerEntity.setOrders(orders);
        }
        customerEntity.setId(customerRequestDTO.getId());
        customerEntity.setName(customerRequestDTO.getName());
        customerEntity.setEmail(customerRequestDTO.getEmail());
        customerEntity = repository.save(customerEntity);
        log.info("Nessun customer trovato, lo creo {}",customerEntity.getId());
        return mapper.entityToDTO(customerEntity);
    }

    @Cacheable(value="customers", key="#id")
    @Transactional(readOnly = true)
    public CustomerResponse findById(Long id) {

        CustomerEntity customerEntity = repository.findById(id).orElseThrow(() -> new AppException("Customer " + id + " not found", HttpStatus.NOT_FOUND));
        if (customerEntity.getOrders() != null && !customerEntity.getOrders().isEmpty()) {
            List<OrderEntity> orders = customerEntity.getOrders().stream()
                    .map(orderRequest ->
                            orderRepository.findById(orderRequest.getId())
                                    .orElseThrow(() ->
                                            new RuntimeException("Order not found")
                                    )
                    )
                    .toList();
            customerEntity.setOrders(orders);
        }
        CustomerRequestDTO customerDTO = mapper.toDto(customerEntity);
        return mapper.toResponse(customerDTO);
    }

}


