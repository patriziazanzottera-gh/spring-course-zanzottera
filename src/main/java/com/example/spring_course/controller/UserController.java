package com.example.spring_course.controller;

import com.example.spring_course.model.UserRequest;
import com.example.spring_course.model.UserResponse;
import com.example.spring_course.service.UserService;
import com.example.spring_course.service.dto.UserRequestDTO;
import com.example.spring_course.service.mapper.MapperUserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        return service.getByName(nome);
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long id) {

        return service.getById(id);
    }

    @GetMapping("/byNameNative/{nome}")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable("nome") String nome) {
        return service.getByNameNative(nome);
    }

    @PostMapping("/createUser")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        return service.createUser(mapperUserDTO.toRequestDTO(userRequest));
    }

    @PostMapping("/updateUser")
    public ResponseEntity<UserResponse> updateUser(@Valid @RequestBody UserRequest userRequest) {
        return service.updateUser(mapperUserDTO.toRequestDTO(userRequest));
    }

}
