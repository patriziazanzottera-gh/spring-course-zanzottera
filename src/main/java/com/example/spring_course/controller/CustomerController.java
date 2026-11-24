package com.example.spring_course.controller;

import com.example.spring_course.controller.model.CustomerRequest;
import com.example.spring_course.controller.model.CustomerResponse;
import com.example.spring_course.service.CustomerService;
import com.example.spring_course.service.dto.CustomerResponseDTO;
import com.example.spring_course.service.mapper.CustomerMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService service;
    private final CustomerMapper customerMapper;

    @Operation(summary = "crea un nuovo customer")
    @ApiResponse(responseCode = "200"
            , description = "Custome creato correttamente"
            , content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE
            , schema =  @Schema(implementation = CustomerResponse.class)
    ))

    @PostMapping("/create")
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        CustomerResponseDTO customerResponseDTO = service.createCustomer(customerMapper.toRequestDTO(customerRequest));
        CustomerResponse userResponse = customerMapper.dtoToResponse(customerResponseDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable("id") Long id) {
        CustomerResponse customerResponse = service.findById(id);
        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }

}
