package com.example.spring_course.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateClienteRequest {


    @NotNull(message = "Il nome del cliente deve essere valorizzato")
    @Pattern(
            regexp = "^.{2,50}$",
            message = "Il nome deve contenere da 2 a 50 caratteri"
    )
    private String nome;

    @NotNull(message = "Il cognome del cliente deve essere valorizzato")
    @Pattern(
            regexp = "^(?=.*\\S).{2,50}$",
            message = "Il cognome deve contenere da 2 a 50 caratteri"
    )
    private String cognome;

    @NotNull(message = "L'indirizzo email deve essere valorizzato")
    @Email(message = "L'indirizzo email deve essere valido")
    private String email;

    @Pattern(
            regexp = "^[0-9]{0,20}$",
            message = "telefono: Inserire un numero composto massimo 20 cifre"
    )
    private String telefono;

    @NotNull(message = "L'indirizzo deve essere valorizzato")
    @Pattern(
            regexp = "^(?=.*\\S).{5,120}$",
            message = "indirizzo: Il campo deve contenere da 5 a 120 caratteri"
    )
    private String indirizzo;

    @NotNull(message = "La citta deve essere valorizzata")
    @Pattern(
            regexp = "^(?=.*\\S).{2,50}$",
            message = "citta: Il campo deve contenere da 2 a 50 caratteri"
    )
    private String citta;

    @NotNull (message = "Il cap deve essere valorizzato")
    @Pattern(
            regexp = "^[0-9]{5}$",
            message = "cap: Inserire esattamente 5 cifre numeriche"
    )
    private String cap;
}
