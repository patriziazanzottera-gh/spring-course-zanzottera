package com.example.spring_course.service.dto;

import com.example.spring_course.repository.model.Stato;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticheStatoResponseDTO {

    private Long numeroOrdini;
    private Stato stato;

}