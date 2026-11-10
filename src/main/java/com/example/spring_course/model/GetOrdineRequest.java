package com.example.spring_course.model;

import com.example.spring_course.repository.model.Stato;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetOrdineRequest {

   private Long id;
}
