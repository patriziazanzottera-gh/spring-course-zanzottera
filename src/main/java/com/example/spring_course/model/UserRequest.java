package com.example.spring_course.model;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank
    private String nome;
    @Email(message = "L'email deve rispettare il formato che tutti sanno")
    @NotBlank(message="Email obbligatoria")
    private String email;
    @Pattern(regexp = "([A-Z]{2})")
    private String citta;
    @Pattern(regexp = "([A-Z]{2})")
    private String nazione;
    @Min(value = 1, message = "Ti pare? L'età deve essere sensata")
    @NotNull
    private int eta;

    public UserRequest(String nome , String email, String citta, String nazione, int eta) {
        this.nome = nome;
        this.email = email;
        this.citta = citta;
        this.nazione = nazione;
        this.eta=eta;
    }
}
