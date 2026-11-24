package com.example.spring_course.controller.model;

import com.example.spring_course.repository.model.OrderEntity;
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
    private List<OrderRequest> orders;
}
