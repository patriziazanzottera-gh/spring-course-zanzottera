package com.example.spring_course.service;

import com.example.spring_course.controller.model.CustomerResponse;
import com.example.spring_course.exception.AppException;
import com.example.spring_course.repository.CustomerRepository;
import com.example.spring_course.repository.model.CustomerEntity;
import com.example.spring_course.service.dto.CustomerRequestDTO;
import com.example.spring_course.service.dto.CustomerResponseDTO;
import com.example.spring_course.service.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public CustomerResponseDTO createCustomer(CustomerRequestDTO customerRequestDTO) {
        repository.findById(customerRequestDTO.getId()).ifPresent(ordine -> {
            log.error("Ordine già esistente {}",customerRequestDTO.getId());
            throw new AppException("Ordine " + customerRequestDTO.getId() + " già presente",
                    HttpStatus.CONFLICT
            );
        });

        CustomerEntity customerEntity = mapper.dtoToEntity(customerRequestDTO);
        customerEntity = repository.save(customerEntity);
        log.info("Nessun utente trovato, lo creo {}",customerEntity.getId());

        return mapper.entityToDTO(customerEntity);
    }

    @Cacheable(value="customers", key="#id")
    @Transactional(readOnly = true)
    public CustomerResponse findById(Long id) {
        CustomerEntity customerEntity = repository.findById(id).orElseThrow(() -> new AppException("Customer " + id + " not found", HttpStatus.NOT_FOUND));
        CustomerRequestDTO customerDTO = mapper.toDto(customerEntity);
        return mapper.toResponse(customerDTO);
    }

}


