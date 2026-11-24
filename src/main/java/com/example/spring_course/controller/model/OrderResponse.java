package com.example.spring_course.controller.model;

import com.example.spring_course.repository.model.CustomerEntity;
import com.example.spring_course.repository.model.OrderEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;
    private BigDecimal total;
    private CustomerResponse customer;

    public OrderResponse(Long id, BigDecimal total) {
        this.id = id;
        this.total = total;
    }
}
