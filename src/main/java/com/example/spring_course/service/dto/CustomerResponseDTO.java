package com.example.spring_course.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDTO {

    private Long id;
    private String name;
    private String email;
    private List<OrderResponseDTO> orders;

    public CustomerResponseDTO(Long id , String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
