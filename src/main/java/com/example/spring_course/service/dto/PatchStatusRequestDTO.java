package com.example.spring_course.service.dto;

import com.example.spring_course.repository.model.Stato;
import lombok.Data;

@Data
public class PatchStatusRequestDTO {

    private Long id;
    private Stato stato;
}
