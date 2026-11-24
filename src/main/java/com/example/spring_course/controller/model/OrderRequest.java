package com.example.spring_course.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    private Long id;
    private BigDecimal total;
    private CustomerRequest customer;

    public OrderRequest(Long id, BigDecimal total) {
        this.id = id;
        this.total = total;
    }
}
