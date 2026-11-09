package com.example.spring_course.service.dto;

import com.example.spring_course.repository.model.Stato;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrdineRequestDTO {

    private String codiceOrdine;
    private String nomeCliente;
    private String cognomeCliente;
    private String emailCliente;
    private String telefonoCliente;
    private String indirizzoSpedizione;
    private String cittaSpedizione;
    private String cap;
    private String prodotto;
    private Integer quantita;
    private Double prezzoUnitario;
    private Double totale;
    private Stato stato;
    private LocalDateTime dataOrdine;

}
