package com.example.spring_course.controller.advice;

import com.example.spring_course.controller.advice.model.ApiErrorResponse;
import com.example.spring_course.exception.AppException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiErrorResponse> hanldeAppException(AppException e, HttpServletRequest request) {
        ApiErrorResponse response = new ApiErrorResponse();
        response.setTimestamp(LocalDateTime.now());
        response.setAppErrorMessage(e.getMessage());
        response.setAppErrorCode(e.getStatusCode().getReasonPhrase());
        response.setHttpStatusCode(e.getStatusCode());
        response.setPath(request.getRequestURI());
        return ResponseEntity.status(e.getStatusCode()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> hanldeMethodException(MethodArgumentNotValidException e, HttpServletRequest request) {
        String message=e.getBindingResult().getFieldErrors().stream()
                .map(error->error.getDefaultMessage()).collect(Collectors.joining(","));
        ApiErrorResponse response = new ApiErrorResponse();
        response.setAppErrorMessage(message);
        response.setTimestamp(LocalDateTime.now());
        response.setAppErrorCode(HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.setHttpStatusCode(HttpStatus.BAD_REQUEST);
        response.setPath(request.getRequestURI());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> hanldeAllGenericException(Exception e, HttpServletRequest request) {
        String message=e.getMessage();
        ApiErrorResponse response = new ApiErrorResponse();
        response.setAppErrorMessage(message);
        response.setTimestamp(LocalDateTime.now());
        response.setAppErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        response.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        response.setPath(request.getRequestURI());
        return ResponseEntity.internalServerError().body(response);
    }

}

