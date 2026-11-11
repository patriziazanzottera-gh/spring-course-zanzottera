package com.example.spring_course.controller;

import com.example.spring_course.model.ExternalUserResponse;
import com.example.spring_course.service.ExternalUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/external-users")
public class ExternalUserController {

    private final ExternalUserService service;

    @GetMapping("/{id}")
    public ExternalUserResponse findUserById(@PathVariable Long id) {
        return service.findUserByd(id);
    }
}
