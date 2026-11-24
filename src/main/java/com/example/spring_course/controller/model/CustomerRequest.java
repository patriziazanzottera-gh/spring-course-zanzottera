package com.example.spring_course.controller.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {

    private Long id;
    private String name;
    @NotBlank
    @Email
    private String email;
    private List<OrderRequest> orders;
}
