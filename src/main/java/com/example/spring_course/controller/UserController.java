package com.example.spring_course.controller;

import com.example.spring_course.model.UserRequest;
import com.example.spring_course.model.UserResponse;
import com.example.spring_course.service.UserService;

import com.example.spring_course.service.dto.UserResponseDTO;
import com.example.spring_course.service.mapper.MapperUserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final MapperUserDTO mapperUserDTO;
    private final UserService service;

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/byName/{nome}")
    public ResponseEntity<UserResponse> getUserByName(@PathVariable("nome") String nome) {
        UserResponseDTO userResponseDTO = service.getByName(nome);
        UserResponse userResponse = mapperUserDTO.dtoToResponse(userResponseDTO);

        return ResponseEntity.status(userResponse.getStatus()).body(userResponse);
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long id) {
        UserResponseDTO userResponseDTO = service.getById(id);
        UserResponse userResponse = mapperUserDTO.dtoToResponse(userResponseDTO);

        return ResponseEntity.status(userResponse.getStatus()).body(userResponse);
    }

    @PostMapping("/createUser")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        UserResponseDTO userResponseDTO = service.createUser(mapperUserDTO.toRequestDTO(userRequest));
        UserResponse userResponse = mapperUserDTO.dtoToResponse(userResponseDTO);

        return ResponseEntity.status(userResponse.getStatus()).body(userResponse);
    }

    @PostMapping("/updateUser")
    public ResponseEntity<UserResponse> updateUser(@Valid @RequestBody UserRequest userRequest) {
        UserResponseDTO userResponseDTO = service.updateUser(mapperUserDTO.toRequestDTO(userRequest));
        UserResponse userResponse = mapperUserDTO.dtoToResponse(userResponseDTO);

        return ResponseEntity.status(userResponse.getStatus()).body(userResponse);
    }
}