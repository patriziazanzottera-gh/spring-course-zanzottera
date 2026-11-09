package com.example.spring_course.model;

import com.example.spring_course.repository.model.Stato;
import com.example.spring_course.service.dto.OrdineRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrdineResponse {

    private Long id;
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

    private HttpStatus httpStatus;
    private String messaggio;

    public OrdineResponse(String messaggio, Long id, OrdineRequestDTO ordineRequestDTO, HttpStatus httpStatus) {
        this.messaggio = messaggio;
        this.id=id;
        this.codiceOrdine = ordineRequestDTO.getCodiceOrdine();
        this.nomeCliente = ordineRequestDTO.getNomeCliente();
        this.cognomeCliente = ordineRequestDTO.getCognomeCliente();
        this.emailCliente = ordineRequestDTO.getEmailCliente();
        this.telefonoCliente = ordineRequestDTO.getTelefonoCliente();
        this.indirizzoSpedizione = ordineRequestDTO.getIndirizzoSpedizione();
        this.cittaSpedizione = ordineRequestDTO.getCittaSpedizione();
        this.cap = ordineRequestDTO.getCap();
        this.prodotto = ordineRequestDTO.getProdotto();
        this.quantita = ordineRequestDTO.getQuantita();
        this.prezzoUnitario = ordineRequestDTO.getPrezzoUnitario();
        this.totale = ordineRequestDTO.getTotale();
        this.stato = ordineRequestDTO.getStato();
        this.dataOrdine = ordineRequestDTO.getDataOrdine();
        this.httpStatus=httpStatus;
    }
}
