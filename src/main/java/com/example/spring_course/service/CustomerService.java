package com.example.spring_course.service;

import com.example.spring_course.exception.AppException;
import com.example.spring_course.model.CustomerResponse;
import com.example.spring_course.repository.CustomersRepository;
import com.example.spring_course.repository.model.CustomerEntity;
import com.example.spring_course.service.dto.CustomerDTO;
import com.example.spring_course.service.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomersRepository repository;
    private final CustomerMapper mapper;
    
    @Transactional(readOnly = true)
    public CustomerResponse findById(Long id) {
        CustomerEntity customerEntity = repository.findById(id).orElseThrow(() -> new AppException("Customer " + id + " not found", HttpStatus.NOT_FOUND));
        CustomerDTO customerDTO = mapper.toDto(customerEntity);
        return mapper.toResponse(customerDTO);
    }
}


