package com.example.spring_course.model;

import com.example.spring_course.repository.model.Stato;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

public record ProductRequest (
    @NotBlank
    String name,
    String description,
    @NotNull
    @DecimalMin("0.01")
    BigDecimal price,
    @NotBlank
    String category,
    String brand,
    Map<String, Object> technicalDetails
){}
