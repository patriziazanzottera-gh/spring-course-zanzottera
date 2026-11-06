package com.example.spring_course.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private Long id;
    private String name;
    private List<OrderDTO> orders;

    public CustomerDTO(Long id , String name) {
        this.id = id;
        this.name = name;
    }
}
