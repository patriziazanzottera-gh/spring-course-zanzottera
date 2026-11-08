package com.example.spring_course.model;

import com.example.spring_course.service.dto.UserRequestDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NotBlank
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String userId;
    private String nome;
    private String email;
    private int eta;
    private String indirizzo;
    private String message;
    private HttpStatus status;

    public UserResponse(String message,  String oid, UserRequestDTO user, HttpStatus status) {
        this.message = message;
        this.userId = oid;
        this.nome = user.getNome();
        this.email = user.getEmail();
        this.indirizzo = user.getIndirizzo();
        this.status = status;

    }

    public UserResponse(String message) {
        this.message = message;
    }

    public UserResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}