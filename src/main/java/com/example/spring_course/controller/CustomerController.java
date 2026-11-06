package com.example.spring_course.controller;

import com.example.spring_course.model.CustomerResponse;
import com.example.spring_course.model.UserResponse;
import com.example.spring_course.service.CustomerService;
import com.example.spring_course.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService service;

    @GetMapping("/byId/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable("id") Long id) {
        CustomerResponse customerResponse = service.findById(id);
        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }

}
