package com.example.spring_course.repository.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Data
@Table(name="prodotto")
public class ProdottoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @UniqueElements
    @Column(name="CODICE")
    private String codicee;

    @NotNull
    @Column(name="NOME")
    private String nome;

    @Column(name="DESCRIZIONE")
    private String descrizione;

    @NotNull
    @Column(name="PREZZO")
    @Positive
    private Double prezzo;

    @NotNull
    @Column(name="DISPONIBILE")
    private Boolean disponibile;
}

