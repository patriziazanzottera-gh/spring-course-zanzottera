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
public class CreateRigaOrdineRequest {


    private String codiceOrdine;


    @NotNull(message = "La quantità deve essere valorizzata")
    @Min(value = 1, message = "Il valore minimo è 1")
    @Max(value = 999, message = "Il valore massimo è 999")
    private Integer quantita;

    @NotNull(message = "Il prezzo unitario deve essere valorizzato")
    @Positive
    private Double prezzoUnitario;

    private Double totaleRiga;
}
