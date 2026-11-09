package com.example.spring_course.model;

import com.example.spring_course.repository.model.OrdineEntity;
import com.example.spring_course.repository.model.Stato;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateOrdineResponse {

    private Long id;
    private String codiceOrdine;
    private LocalDateTime dataOrdine;
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

    private HttpStatus httpStatus;


    public CreateOrdineResponse(OrdineEntity ordineEntity, HttpStatus httpStatusPar) {

        this.id=ordineEntity.getId();
        this.codiceOrdine = ordineEntity.getCodiceOrdine();
        this.nomeCliente = ordineEntity.getNomeCliente();
        this.cognomeCliente = ordineEntity.getCognomeCliente();
        this.emailCliente = ordineEntity.getEmailCliente();
        this.telefonoCliente = ordineEntity.getTelefonoCliente();
        this.indirizzoSpedizione = ordineEntity.getIndirizzoSpedizione();
        this.cittaSpedizione = ordineEntity.getCittaSpedizione();
        this.cap = ordineEntity.getCap();
        this.prodotto = ordineEntity.getProdotto();
        this.quantita = ordineEntity.getQuantita();
        this.prezzoUnitario = ordineEntity.getPrezzoUnitario();
        this.totale = ordineEntity.getTotale();
        this.stato = ordineEntity.getStato();
        this.dataOrdine = ordineEntity.getDataOrdine();
        this.httpStatus=httpStatusPar;
    }
}
