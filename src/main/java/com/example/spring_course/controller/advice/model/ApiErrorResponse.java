package com.example.spring_course.controller.advice.model;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApiErrorResponse {

    private LocalDateTime timestamp;
    private HttpStatus httpStatusCode;
    private String appErrorCode;
    private String appErrorMessage;
    private String path;


    @Data
    public static class ErrorMessage {
        private String path;
        private String message;
    }


}
