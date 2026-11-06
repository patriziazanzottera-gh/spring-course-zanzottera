package com.example.spring_course.model;

import com.example.spring_course.service.dto.UserRequestDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NotBlank
@NoArgsConstructor
public class UserResponse {

    private String userId;
    private String nome;
    private String email;
    private String message;
    private String indirizzo;
    private int eta;

    public UserResponse(String message, String oid, UserRequestDTO user) {
        this.message = message;
        this.userId = oid;
        this.nome = user.getNome();
        this.email = user.getEmail();
        this.eta = user.getEta();
        this.indirizzo = user.getIndirizzo();
    }

    public UserResponse(String message) {
        this.message = message;
    }
}