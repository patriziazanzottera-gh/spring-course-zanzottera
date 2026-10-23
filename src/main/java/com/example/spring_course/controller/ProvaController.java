package com.example.spring_course.controller;

import com.example.spring_course.model.UserRequest;
import com.example.spring_course.model.UserResponse;
import com.example.spring_course.service.ProvaService;
import com.example.spring_course.service.dto.UserRequestDTO;
import com.example.spring_course.service.mapper.MapperUserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ProvaController{

    private final MapperUserDTO mapperUserDTO;
    private final ProvaService service;

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/byName/{nome}")
    public ResponseEntity<UserResponse> getUserByName(@PathVariable("nome") String nome) {
        UserResponse userResponse = null;
        if (nome == null || nome.isBlank()) {
            userResponse = new UserResponse("Il Parametro 'nome' deve essere valorizzato ");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userResponse);
        }
        userResponse = new UserResponse("Parametro valorizzato");
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest user) {
        UserRequestDTO requestDTO = mapperUserDTO.toRequestDTO(user);
        return service.createUser(requestDTO);
    }

    @GetMapping("/usersInGet")
    public ResponseEntity<UserResponse> createUserInGet(@Valid @RequestBody UserRequest user){
        UserRequestDTO requestDTO = mapperUserDTO.toRequestDTO(user);
        return service.createUser(requestDTO);
    }
}
