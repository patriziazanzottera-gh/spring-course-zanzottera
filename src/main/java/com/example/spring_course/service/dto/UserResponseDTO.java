package com.example.spring_course.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private String nome;
    private String email;
    private String indirizzo;
    private int eta;
    private String message;
    private HttpStatus status;
}
