package com.example.spring_course.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDTO {

    private Long id;
    private String name;
    private List<OrderRequestDTO> orders;

    public CustomerRequestDTO(Long id , String name) {
        this.id = id;
        this.name = name;
    }
}
