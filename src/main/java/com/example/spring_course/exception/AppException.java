package com.example.spring_course.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends RuntimeException {

    private HttpStatus statusCode;
    public AppException(String message, HttpStatus statusCode) {
        super();
        this.statusCode = statusCode;
    }
}
