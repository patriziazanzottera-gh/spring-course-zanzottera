package com.example.spring_course.service.dto;

import com.example.spring_course.repository.model.Stato;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateClienteRequestDTO {

    private String nome;
    private String cognome;
    private String email;
    private String telefono;
    private String indirizzo;
    private String citta;
    private String cap;

}
