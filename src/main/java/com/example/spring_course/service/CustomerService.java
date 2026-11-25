package com.example.spring_course.service;

import com.example.spring_course.controller.model.CustomerRequest;
import com.example.spring_course.controller.model.CustomerResponse;
import com.example.spring_course.controller.model.OrderRequest;
import com.example.spring_course.controller.model.OrderResponse;
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
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;


@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final OrderRepository orderRepository;
    private final CustomerMapper mapper;
    private final CacheManager cacheManager;

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

    @Transactional
    @CacheEvict(value = "customers", key = "#id")
    public CustomerResponse update(Long id, CustomerRequest request) {

        CustomerEntity customerEntity = repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                NOT_FOUND,
                                "Customer not found: " +
                                        id
                        )
                );

        customerEntity.setName(request.getName());
        customerEntity.setEmail(request.getEmail());

        if (request.getOrders() != null) {
            // Gli ordini passati vengono AGGIUNTI a quelli già associati (nessuno viene dissociato).
            // OrderEntity è il lato proprietario della relazione (customer_id): va aggiornato order.customer
            for (OrderRequest orderRequest : request.getOrders()) {
                OrderEntity order = orderRepository.findById(orderRequest.getId())
                        .orElseThrow(() -> new AppException(
                                "Order " + orderRequest.getId() + " not found", HttpStatus.NOT_FOUND));

                CustomerEntity previousCustomer = order.getCustomer();
                if (previousCustomer != null) {
                    if (previousCustomer.getId().equals(customerEntity.getId())) {
                        continue; // già associato a questo customer
                    }
                    // l'ordine viene spostato: lo tolgo dal customer precedente e ne svuoto la cache
                    previousCustomer.getOrders().remove(order);
                    Cache cache = cacheManager.getCache("customers");
                    if (cache != null) {
                        cache.evict(previousCustomer.getId());
                    }
                }

                order.setCustomer(customerEntity);
                customerEntity.getOrders().add(order);
            }
        }

        customerEntity = repository.save(customerEntity);
        return mapper.entityToResponse(customerEntity);
    }

    @CacheEvict(value = "customers", key = "#id")
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    NOT_FOUND,
                    "Customer not found: " + id
            );
        }
        repository.deleteById(id);
    }
}


