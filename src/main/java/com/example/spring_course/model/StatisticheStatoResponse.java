package com.example.spring_course.model;

import com.example.spring_course.repository.model.OrdineEntity;
import com.example.spring_course.repository.model.Stato;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StatisticheStatoResponse {

    private Long numeroOrdini;
    private Stato stato;
}
