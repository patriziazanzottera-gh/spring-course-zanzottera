package com.example.spring_course.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

public record ProductResponse (
    String id,
    String name,
    String description,
    BigDecimal price,
    String category,
    Instant createdAt,
    String brand,
    Map<String, Object> technicalDetails
){}
