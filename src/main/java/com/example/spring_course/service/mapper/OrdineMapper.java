package com.example.spring_course.service.mapper;

import com.example.spring_course.model.OrdineRequest;
import com.example.spring_course.model.OrdineResponse;
import com.example.spring_course.repository.model.OrdineEntity;
import com.example.spring_course.service.dto.OrdineRequestDTO;
import com.example.spring_course.service.dto.OrdineResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class OrdineMapper {

    @Mapping(target = "totale", expression = "java(calcolaTotale(ordineRequest.getPrezzoUnitario(), ordineRequest.getQuantita()))")
    public abstract OrdineRequestDTO toRequestDTO (OrdineRequest ordineRequest);

    public Double calcolaTotale(Double prezzoUnitario, Integer quantita) {
        return prezzoUnitario*quantita;
    }

    public OrdineEntity dtoToEntity(OrdineRequestDTO ordineRequestDTO) {
        OrdineEntity ordineEntity = new OrdineEntity();

        ordineEntity.setCodiceOrdine(ordineRequestDTO.getCodiceOrdine());
        ordineEntity.setNomeCliente(ordineRequestDTO.getNomeCliente());
        ordineEntity.setCognomeCliente(ordineRequestDTO.getCognomeCliente());
        ordineEntity.setEmailCliente(ordineRequestDTO.getEmailCliente());
        ordineEntity.setTelefonoCliente(ordineRequestDTO.getTelefonoCliente());
        ordineEntity.setIndirizzoSpedizione(ordineRequestDTO.getIndirizzoSpedizione());
        ordineEntity.setCittaSpedizione(ordineRequestDTO.getCittaSpedizione());
        ordineEntity.setCap(ordineRequestDTO.getCap());
        ordineEntity.setProdotto(ordineRequestDTO.getProdotto());
        ordineEntity.setQuantita(ordineRequestDTO.getQuantita());
        ordineEntity.setPrezzoUnitario(ordineRequestDTO.getPrezzoUnitario());
        ordineEntity.setTotale(ordineRequestDTO.getTotale());
        ordineEntity.setStato(ordineRequestDTO.getStato());
        ordineEntity.setDataOrdine(ordineRequestDTO.getDataOrdine());

        return ordineEntity;
    }

    public OrdineResponseDTO responseToDTO(OrdineResponse ordineResponse) {
        OrdineResponseDTO ordineResponseDTO = new OrdineResponseDTO();
        ordineResponseDTO.setCodiceOrdine(ordineResponse.getCodiceOrdine());
        ordineResponseDTO.setNomeCliente(ordineResponse.getNomeCliente());
        ordineResponseDTO.setCognomeCliente(ordineResponse.getCognomeCliente());
        ordineResponseDTO.setEmailCliente(ordineResponse.getEmailCliente());
        ordineResponseDTO.setTelefonoCliente(ordineResponse.getTelefonoCliente());
        ordineResponseDTO.setIndirizzoSpedizione(ordineResponse.getIndirizzoSpedizione());
        ordineResponseDTO.setCittaSpedizione(ordineResponse.getCittaSpedizione());
        ordineResponseDTO.setCap(ordineResponse.getCap());
        ordineResponseDTO.setProdotto(ordineResponse.getProdotto());
        ordineResponseDTO.setQuantita(ordineResponse.getQuantita());
        ordineResponseDTO.setPrezzoUnitario(ordineResponse.getPrezzoUnitario());
        ordineResponseDTO.setTotale(ordineResponse.getTotale());
        ordineResponseDTO.setStato(ordineResponse.getStato());
        ordineResponseDTO.setDataOrdine(ordineResponse.getDataOrdine());
        return ordineResponseDTO;
    }

    public OrdineResponse dtoToResponse(OrdineResponseDTO ordineResponseDTO) {
        OrdineResponse ordineResponse = new OrdineResponse();
        ordineResponse.setCodiceOrdine(ordineResponseDTO.getCodiceOrdine());
        ordineResponse.setNomeCliente(ordineResponseDTO.getNomeCliente());
        ordineResponse.setCognomeCliente(ordineResponseDTO.getCognomeCliente());
        ordineResponse.setEmailCliente(ordineResponseDTO.getEmailCliente());
        ordineResponse.setTelefonoCliente(ordineResponseDTO.getTelefonoCliente());
        ordineResponse.setIndirizzoSpedizione(ordineResponseDTO.getIndirizzoSpedizione());
        ordineResponse.setCittaSpedizione(ordineResponseDTO.getCittaSpedizione());
        ordineResponse.setCap(ordineResponseDTO.getCap());
        ordineResponse.setProdotto(ordineResponseDTO.getProdotto());
        ordineResponse.setQuantita(ordineResponseDTO.getQuantita());
        ordineResponse.setPrezzoUnitario(ordineResponseDTO.getPrezzoUnitario());
        ordineResponse.setTotale(ordineResponseDTO.getTotale());
        ordineResponse.setStato(ordineResponseDTO.getStato());
        ordineResponse.setDataOrdine(ordineResponseDTO.getDataOrdine());

        return ordineResponse;
    }

    public OrdineResponseDTO entityToDTO(OrdineEntity ordineEntity) {
        OrdineResponseDTO ordineResponseDTO = new OrdineResponseDTO();

        ordineResponseDTO.setCodiceOrdine(ordineEntity.getCodiceOrdine());
        ordineResponseDTO.setNomeCliente(ordineEntity.getNomeCliente());
        ordineResponseDTO.setCognomeCliente(ordineEntity.getCognomeCliente());
        ordineResponseDTO.setEmailCliente(ordineEntity.getEmailCliente());
        ordineResponseDTO.setTelefonoCliente(ordineEntity.getTelefonoCliente());
        ordineResponseDTO.setIndirizzoSpedizione(ordineEntity.getIndirizzoSpedizione());
        ordineResponseDTO.setCittaSpedizione(ordineEntity.getCittaSpedizione());
        ordineResponseDTO.setCap(ordineEntity.getCap());
        ordineResponseDTO.setProdotto(ordineEntity.getProdotto());
        ordineResponseDTO.setQuantita(ordineEntity.getQuantita());
        ordineResponseDTO.setPrezzoUnitario(ordineEntity.getPrezzoUnitario());
        ordineResponseDTO.setTotale(ordineEntity.getTotale());
        ordineResponseDTO.setStato(ordineEntity.getStato());
        ordineResponseDTO.setDataOrdine(ordineEntity.getDataOrdine());

        return ordineResponseDTO;
    }

    public OrdineResponse entityToResponse(OrdineEntity ordineEntity) {
        OrdineResponse ordineResponse =  new OrdineResponse();
        ordineResponse.setCodiceOrdine(ordineEntity.getCodiceOrdine());
        ordineResponse.setNomeCliente(ordineEntity.getNomeCliente());
        ordineResponse.setCognomeCliente(ordineEntity.getCognomeCliente());
        ordineResponse.setEmailCliente(ordineEntity.getEmailCliente());
        ordineResponse.setTelefonoCliente(ordineEntity.getTelefonoCliente());
        ordineResponse.setIndirizzoSpedizione(ordineEntity.getIndirizzoSpedizione());
        ordineResponse.setCittaSpedizione(ordineEntity.getCittaSpedizione());
        ordineResponse.setCap(ordineEntity.getCap());
        ordineResponse.setProdotto(ordineEntity.getProdotto());
        ordineResponse.setQuantita(ordineEntity.getQuantita());
        ordineResponse.setPrezzoUnitario(ordineEntity.getPrezzoUnitario());
        ordineResponse.setTotale(ordineEntity.getTotale());
        ordineResponse.setStato(ordineEntity.getStato());
        ordineResponse.setDataOrdine(ordineEntity.getDataOrdine());

        return ordineResponse;
    }

    public OrdineEntity responseDtoToEntity(OrdineResponseDTO ordineResponseDTO) {
        OrdineEntity ordineEntity = new OrdineEntity();
        ordineEntity.setCodiceOrdine(ordineResponseDTO.getCodiceOrdine());
        ordineEntity.setNomeCliente(ordineResponseDTO.getNomeCliente());
        ordineEntity.setCognomeCliente(ordineResponseDTO.getCognomeCliente());
        ordineEntity.setEmailCliente(ordineResponseDTO.getEmailCliente());
        ordineEntity.setTelefonoCliente(ordineResponseDTO.getTelefonoCliente());
        ordineEntity.setIndirizzoSpedizione(ordineResponseDTO.getIndirizzoSpedizione());
        ordineEntity.setCittaSpedizione(ordineResponseDTO.getCittaSpedizione());
        ordineEntity.setCap(ordineResponseDTO.getCap());
        ordineEntity.setProdotto(ordineResponseDTO.getProdotto());
        ordineEntity.setQuantita(ordineResponseDTO.getQuantita());
        ordineEntity.setPrezzoUnitario(ordineResponseDTO.getPrezzoUnitario());
        ordineEntity.setTotale(ordineResponseDTO.getTotale());
        ordineEntity.setStato(ordineResponseDTO.getStato());
        ordineEntity.setDataOrdine(ordineResponseDTO.getDataOrdine());
        return ordineEntity;
    }
}