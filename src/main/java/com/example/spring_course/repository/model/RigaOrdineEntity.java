package com.example.spring_course.repository.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name="RigaOrdine")
public class RigaOrdineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODOTTO_ID", nullable = false)
    private ProdottoEntity prodotto;

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
    @Column(name="TOTALE_RIGA")
    @Positive
    private Double totaleRiga;

   /* @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDINE_ID", nullable = false)
    private OrdineEntity ordine;*/
}
