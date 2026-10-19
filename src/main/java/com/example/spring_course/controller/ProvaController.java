package com.example.spring_course.controller;

import com.example.spring_course.model.UserRequest;
import com.example.spring_course.model.UserResponse;
import com.example.spring_course.service.dto.UserDTO;
import com.example.spring_course.service.mapper.MapperUserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ProvaController {

    private final MapperUserDTO mapperUserDTO;

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(@Valid RequestBody UserRequest user){

        UserRequest userDTO = mapperUserDTO.toRequestDTO(user);
        return null;
    }
}
