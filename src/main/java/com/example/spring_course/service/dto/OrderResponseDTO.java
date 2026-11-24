package com.example.spring_course.service.dto;

import com.example.spring_course.repository.model.CustomerEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {

    private Long id;
    private BigDecimal total;
    private CustomerResponseDTO customer;

    public OrderResponseDTO(Long id, BigDecimal total) {
        this.id = id;
        this.total = total;
    }
}
