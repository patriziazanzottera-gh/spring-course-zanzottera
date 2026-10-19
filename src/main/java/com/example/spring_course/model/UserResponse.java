package com.example.spring_course.model;

import com.example.spring_course.service.dto.UserRequestDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@NotBlank
public class UserResponse {

    private String userId;
    private String nome;
    private String email;
    private String message;

    public UserResponse(String message,  String oid) {
        this.message = message;
        this.userId = oid;
        //this.nome = user.getNome();
       // this.email = user.getEmail();
    }

    public UserResponse(String message) {
        this.message = message;
    }
}
