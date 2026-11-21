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
public class CreateOrdineRequest {


    private String codiceOrdine;

    @NotNull(message = "Il nome del cliente deve essere valorizzato")
    @Pattern(
            regexp = "^.{2,50}$",
            message = "Il nomeCliente deve contenere da 2 a 50 caratteri"
    )
    private String nomeCliente;

    @NotNull(message = "Il cognome del cliente deve essere valorizzato")
    @Pattern(
            regexp = "^(?=.*\\S).{2,50}$",
            message = "Il cognomeCliente deve contenere da 2 a 50 caratteri"
    )
    private String cognomeCliente;

    @NotNull(message = "L'indirizzo email deve essere valorizzato")
    @Email(message = "L'indirizzo email deve essere valido")
    private String emailCliente;

    @Pattern(
            regexp = "^[0-9]{0,20}$",
            message = "telefonoCliente: Inserire un numero composto massimo 20 cifre"
    )
    private String telefonoCliente;

    @NotNull(message = "L'indirizzo di spedizione deve essere valorizzato")
    @Pattern(
            regexp = "^(?=.*\\S).{5,120}$",
            message = "indirizzoSpedizione: Il campo deve contenere da 5 a 120 caratteri"
    )
    private String indirizzoSpedizione;

    @NotNull(message = "La citta di spedizione deve essere valorizzato")
    @Pattern(
            regexp = "^(?=.*\\S).{2,50}$",
            message = "cittaSpedizione: Il campo deve contenere da 2 a 50 caratteri"
    )
    private String cittaSpedizione;

    @NotNull (message = "Il cap deve essere valorizzato")
    @Pattern(
            regexp = "^[0-9]{5}$",
            message = "cap: Inserire esattamente 5 cifre numeriche"
    )
    private String cap;

    @NotNull(message = "Il prodotto deve essere valorizzato")
    @Pattern(
            regexp = "^(?=.*\\S).{2,80}$",
            message = "Il campo deve contenere da 2 a 80 caratteri"
    )
    private String prodotto;

    @NotNull(message = "La quantità deve essere valorizzata")
    @Min(value = 1, message = "Il valore minimo è 1")
    @Max(value = 999, message = "Il valore massimo è 999")
    private Integer quantita;

    @NotNull(message = "Il prezzo unitario deve essere valorizzato")
    @Positive
    private Double prezzoUnitario;

    private Double totale;

    private Stato stato;

    private LocalDateTime dataOrdine;
}
