package com.example.spring_course.repository.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Data
@Table(name="Ordine")
public class OrdineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name="CODICE_ORDINE", length = 30)
    @Size(min=20, max=30, message="Il campo può essere composto da un min di 20 ad un massimo di 30 caratteri")
    private String codiceOrdine;

    @NotNull
    @Column(name="NOME_CLIENTE", length = 50)
    @Size(min=2, max=50, message="Il campo può essere composto da un min di 2 ad un massimo di 50 caratteri")
    private String nomeCliente;

    @NotNull
    @Column(name="COGNOME_CLIENTE", length = 50)
    @Size(min=2, max=50, message="Il campo può essere composto da un min di 2 ad un massimo di 50 caratteri")
    private String cognomeCliente;

    @NotNull
    @Email
    @Column(name="EMAIL_CLIENTE", length = 100)
    @Size(max=100, message="Il campo non può superare 100 caratteri")
    private String emailCliente;

    @Column(name="TELEFONO_CLIENTE", length = 20)
    @Size(max=20 , message = "Il campo non può superare 20 caratteri")
    private String telefonoCliente;

    @Column(name="INDIRIZZO_SPEDIZIONE", length = 120)
    @NotNull
    @Size(min=5, max=120, message = "Il campo può essere composto da un min di 5 ad un massimo di 120 caratteri")
    private String indirizzoSpedizione;

    @NotNull
    @Column(name="CITTA_SPEDIZIONE", length = 50)
    @Size(min=2, max=50, message="Il campo può essere composto da un min di 2 ad un massimo di 50 caratteri")
    private String cittaSpedizione;

    @NotNull
    @Column(name="CAP", length = 5)
    @Size(min=5, max=5, message="Il campo deve essere composto esattamente da 5 caratteri")
    private String cap;

    @NotNull
    @Column(name="PRODOTTO", length = 80)
    @Size(min=2, max=80, message="Il campo può essere composto da un min di 2 ad un massimo di 80 caratteri")
    private String prodotto;

    @NotNull
    @Column(name="QUANTITA")
    @Min(value = 1, message = "Il valore minimo è 1")
    @Max(value = 999, message = "Il valore massimo è 999")
    private Integer quantita;

    @NotNull
    @Column(name="PREZZO_UNITARIO")
    @Positive
    private Double prezzoUnitario;

    @NotNull
    @Column(name="TOTALE")
    @Positive
    private Double totale;

    @NotNull
    @Column(name="STATO")
    private Stato stato;

    @NotNull
    @Column(name="DATA_ORDINE")
    private LocalDateTime dataOrdine;
}

