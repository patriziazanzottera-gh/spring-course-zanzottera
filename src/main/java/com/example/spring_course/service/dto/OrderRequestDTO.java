package com.example.spring_course.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {

    private Long id;
    private BigDecimal total;
    private CustomerRequestDTO customer;

    public OrderRequestDTO(Long id, BigDecimal total) {
        this.id = id;
        this.total = total;
    }
}
