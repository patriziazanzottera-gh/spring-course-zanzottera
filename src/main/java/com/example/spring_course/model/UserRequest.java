package com.example.spring_course.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@NotBlank
public class UserRequest {

    private String nome;
    private String email;
    @Pattern(regexp = "([A-Z]{2})")
    private String citta;
    @Pattern(regexp = "([A-Z]{2})")
    private String nazione;
    private int eta;

    public UserRequest(String nome , String email, String citta, String nazione, int eta) {
        this.nome = nome;
        this.email = email;
        this.citta = citta;
        this.nazione = nazione;
        this.eta=eta;
    }
}
