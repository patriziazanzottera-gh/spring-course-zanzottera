package com.example.spring_course.repository.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Data
@Table(name="Cliente")
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name="NOME", length = 30)
    @Size(min=20, max=30, message="Il campo può essere composto da un min di 20 ad un massimo di 30 caratteri")
    private String nome;

    @NotNull
    @Column(name="COGNOME", length = 50)
    @Size(min=2, max=50, message="Il campo può essere composto da un min di 2 ad un massimo di 50 caratteri")
    private String cognome;

    @NotNull
    @Email
    @UniqueElements
    @Column(name="EMAIL", length = 100)
    @Size(max=100, message="Il campo non può superare 100 caratteri")
    private String email;

    @Column(name="TELEFONO", length = 20)
    @Size(max=20 , message = "Il campo non può superare 20 caratteri")
    private String telefono;

    @Column(name="INDIRIZZO")
    @NotNull
    private String indirizzo;

    @NotNull
    @Column(name="CITTA")
    private String citta;

    @NotNull
    @Column(name="CAP", length = 5)
    @Size(min=5, max=5, message="Il campo deve essere composto esattamente da 5 caratteri")
    private String cap;

}

