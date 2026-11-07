package com.example.spring_course.controller;

import com.example.spring_course.model.UserRequest;
import com.example.spring_course.model.UserResponse;
import com.example.spring_course.service.UserService;
import com.example.spring_course.service.dto.UserRequestDTO;
import com.example.spring_course.service.mapper.MapperUserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@Log4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Tag(name="UserController", description = "Controller per CRUD su utente")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
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

    @Operation(summary = "crea un nuovo utente")
    @ApiResponse(responseCode = "200"
            , description = "Utente creato correttamente"
            , content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE
            , examples = @ExampleObject ("""
                        {
                              "email": "pat.zan2@gmaioo.com",
                              "message": "User creato con successo e9c0c4c9-22d9-4d81-870f-8d4ebfb26d38",
                              "nome": "Patrizia2",
                              "userId": "e9c0c4c9-22d9-4d81-870f-8d4ebfb26d38"
                        }
                    """)
            , schema =  @Schema(implementation = UserResponse.class)
    ))
    @PostMapping("/createUser")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        return service.createUser(mapperUserDTO.toRequestDTO(userRequest));
    }

    @PostMapping("/updateUser")
    public ResponseEntity<UserResponse> updateUser(@Valid @RequestBody UserRequest userRequest) {
        return service.updateUser(mapperUserDTO.toRequestDTO(userRequest));
    }
}