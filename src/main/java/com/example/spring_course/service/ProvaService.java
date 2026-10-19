package com.example.spring_course.service;

import com.example.spring_course.model.UserRequest;
import com.example.spring_course.model.UserResponse;
import com.example.spring_course.service.dto.UserRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProvaService {

    public ResponseEntity<UserResponse> createUser(UserRequestDTO user) {
        UUID oid = UUID.randomUUID();
        UserResponse userResponse = new UserResponse("User creato con successo " + oid, oid.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }
}
