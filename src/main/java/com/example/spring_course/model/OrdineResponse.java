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
public class OrdineRequest {

    @NotBlank
    private String codiceOrdine;

    @NotBlank
    @Pattern(
            regexp = "^.{2,50}$",
            message = "Il campo deve contenere da 2 a 50 caratteri"
    )
    private String nomeCliente;

    @NotNull
    @NotBlank
    @Pattern(
            regexp = "^(?=.*\\S).{2,50}$",
            message = "Il campo deve contenere da 2 a 50 caratteri"
    )
    private String cognomeCliente;

    @NotBlank
    @Email
    private String emailCliente;

    @Pattern(
            regexp = "^(?=.*\\S).{2,50}$",
            message = "Il campo deve contenere da 2 a 50 caratteri"
    )

    @Pattern(
            regexp = "^[0-9]{0,20}$",
            message = "Inserire un numero composto massimo 20 cifre"
    )
    private String telefonoCliente;

    @NotNull
    @NotBlank
    @Pattern(
            regexp = "^(?=.*\\S).{50,120}$",
            message = "Il campo deve contenere da 50 a 120 caratteri"
    )
    private String indirizzoSpedizione;

    @NotNull
    @NotBlank
    @Pattern(
            regexp = "^(?=.*\\S).{2,50}$",
            message = "Il campo deve contenere da 2 a 50 caratteri"
    )
    private String cittaSpedizione;

    @NotNull
    @Pattern(
            regexp = "^[0-9]{5}$",
            message = "Inserire esattamente 5 cifre numeriche"
    )
    private String cap;

    @NotNull
    @Pattern(
            regexp = "^(?=.*\\S).{2,80}$",
            message = "Il campo deve contenere da 2 a 80 caratteri"
    )
    private String prodotto;

    @NotNull
    @Min(value = 1, message = "Il valore minimo è 1")
    @Max(value = 999, message = "Il valore massimo è 999")
    private Integer quantita;

    @NotNull
    @Positive
    private Double prezzoUnitario;

    @NotNull
    @Positive
    private Double totale;

    @NotNull
    private Stato stato;

    @NotNull
    private LocalDateTime dataOrdine;
}
