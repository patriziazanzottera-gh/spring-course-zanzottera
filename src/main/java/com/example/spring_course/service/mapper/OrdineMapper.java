package com.example.spring_course.service.mapper;

import com.example.spring_course.model.CreateOrdineRequest;
import com.example.spring_course.model.CreateOrdineResponse;
import com.example.spring_course.model.GetOrdineResponse;
import com.example.spring_course.model.ListOrdineResponse;
import com.example.spring_course.repository.model.OrdineEntity;
import com.example.spring_course.service.dto.CreateOrdineRequestDTO;
import com.example.spring_course.service.dto.CreateOrdineResponseDTO;
import com.example.spring_course.service.dto.GetOrdineResponseDTO;
import com.example.spring_course.service.dto.ListOrdineResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class OrdineMapper {

    @Mapping(target = "totale", expression = "java(calcolaTotale(ordineRequest.getPrezzoUnitario(), ordineRequest.getQuantita()))")
    public abstract CreateOrdineRequestDTO toRequestDTO (CreateOrdineRequest ordineRequest);

    public Double calcolaTotale(Double prezzoUnitario, Integer quantita) {
        return prezzoUnitario*quantita;
    }

    public OrdineEntity dtoToEntity(CreateOrdineRequestDTO ordineRequestDTO) {
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

    public CreateOrdineResponseDTO responseToDTO(CreateOrdineResponse createOrdineResponse) {
        CreateOrdineResponseDTO ordineResponseDTO = new CreateOrdineResponseDTO();
        ordineResponseDTO.setId(createOrdineResponse.getId());
        ordineResponseDTO.setCodiceOrdine(createOrdineResponse.getCodiceOrdine());
        ordineResponseDTO.setNomeCliente(createOrdineResponse.getNomeCliente());
        ordineResponseDTO.setCognomeCliente(createOrdineResponse.getCognomeCliente());
        ordineResponseDTO.setEmailCliente(createOrdineResponse.getEmailCliente());
        ordineResponseDTO.setTelefonoCliente(createOrdineResponse.getTelefonoCliente());
        ordineResponseDTO.setIndirizzoSpedizione(createOrdineResponse.getIndirizzoSpedizione());
        ordineResponseDTO.setCittaSpedizione(createOrdineResponse.getCittaSpedizione());
        ordineResponseDTO.setCap(createOrdineResponse.getCap());
        ordineResponseDTO.setProdotto(createOrdineResponse.getProdotto());
        ordineResponseDTO.setQuantita(createOrdineResponse.getQuantita());
        ordineResponseDTO.setPrezzoUnitario(createOrdineResponse.getPrezzoUnitario());
        ordineResponseDTO.setTotale(createOrdineResponse.getTotale());
        ordineResponseDTO.setStato(createOrdineResponse.getStato());
        ordineResponseDTO.setDataOrdine(createOrdineResponse.getDataOrdine());
        ordineResponseDTO.setHttpStatus(createOrdineResponse.getHttpStatus());
        return ordineResponseDTO;
    }

    public GetOrdineResponseDTO responseToDTOGet(GetOrdineResponse getOrdineResponse) {
        GetOrdineResponseDTO getOrdineResponseDTO = new GetOrdineResponseDTO();
        getOrdineResponseDTO.setId(getOrdineResponse.getId());
        getOrdineResponseDTO.setCodiceOrdine(getOrdineResponse.getCodiceOrdine());
        getOrdineResponseDTO.setNomeCliente(getOrdineResponse.getNomeCliente());
        getOrdineResponseDTO.setCognomeCliente(getOrdineResponse.getCognomeCliente());
        getOrdineResponseDTO.setEmailCliente(getOrdineResponse.getEmailCliente());
        getOrdineResponseDTO.setTelefonoCliente(getOrdineResponse.getTelefonoCliente());
        getOrdineResponseDTO.setIndirizzoSpedizione(getOrdineResponse.getIndirizzoSpedizione());
        getOrdineResponseDTO.setCittaSpedizione(getOrdineResponse.getCittaSpedizione());
        getOrdineResponseDTO.setCap(getOrdineResponse.getCap());
        getOrdineResponseDTO.setProdotto(getOrdineResponse.getProdotto());
        getOrdineResponseDTO.setQuantita(getOrdineResponse.getQuantita());
        getOrdineResponseDTO.setPrezzoUnitario(getOrdineResponse.getPrezzoUnitario());
        getOrdineResponseDTO.setTotale(getOrdineResponse.getTotale());
        getOrdineResponseDTO.setStato(getOrdineResponse.getStato());
        getOrdineResponseDTO.setDataOrdine(getOrdineResponse.getDataOrdine());
        getOrdineResponseDTO.setHttpStatus(getOrdineResponse.getHttpStatus());
        return getOrdineResponseDTO;
    }

    public CreateOrdineResponse dtoToResponse(CreateOrdineResponseDTO ordineResponseDTO) {
        CreateOrdineResponse createOrdineResponse = new CreateOrdineResponse();
        createOrdineResponse.setCodiceOrdine(ordineResponseDTO.getCodiceOrdine());
        createOrdineResponse.setId(ordineResponseDTO.getId());
        createOrdineResponse.setNomeCliente(ordineResponseDTO.getNomeCliente());
        createOrdineResponse.setCognomeCliente(ordineResponseDTO.getCognomeCliente());
        createOrdineResponse.setEmailCliente(ordineResponseDTO.getEmailCliente());
        createOrdineResponse.setTelefonoCliente(ordineResponseDTO.getTelefonoCliente());
        createOrdineResponse.setIndirizzoSpedizione(ordineResponseDTO.getIndirizzoSpedizione());
        createOrdineResponse.setCittaSpedizione(ordineResponseDTO.getCittaSpedizione());
        createOrdineResponse.setCap(ordineResponseDTO.getCap());
        createOrdineResponse.setProdotto(ordineResponseDTO.getProdotto());
        createOrdineResponse.setQuantita(ordineResponseDTO.getQuantita());
        createOrdineResponse.setPrezzoUnitario(ordineResponseDTO.getPrezzoUnitario());
        createOrdineResponse.setTotale(ordineResponseDTO.getTotale());
        createOrdineResponse.setStato(ordineResponseDTO.getStato());
        createOrdineResponse.setDataOrdine(ordineResponseDTO.getDataOrdine());
        createOrdineResponse.setHttpStatus(ordineResponseDTO.getHttpStatus());
        return createOrdineResponse;
    }

    public GetOrdineResponse dtoToResponseGet(GetOrdineResponseDTO getOrdineResponseDTO) {
        GetOrdineResponse getOrdineResponse = new GetOrdineResponse();
        getOrdineResponse.setCodiceOrdine(getOrdineResponseDTO.getCodiceOrdine());
        getOrdineResponse.setId(getOrdineResponseDTO.getId());
        getOrdineResponse.setNomeCliente(getOrdineResponseDTO.getNomeCliente());
        getOrdineResponse.setCognomeCliente(getOrdineResponseDTO.getCognomeCliente());
        getOrdineResponse.setEmailCliente(getOrdineResponseDTO.getEmailCliente());
        getOrdineResponse.setTelefonoCliente(getOrdineResponseDTO.getTelefonoCliente());
        getOrdineResponse.setIndirizzoSpedizione(getOrdineResponseDTO.getIndirizzoSpedizione());
        getOrdineResponse.setCittaSpedizione(getOrdineResponseDTO.getCittaSpedizione());
        getOrdineResponse.setCap(getOrdineResponseDTO.getCap());
        getOrdineResponse.setProdotto(getOrdineResponseDTO.getProdotto());
        getOrdineResponse.setQuantita(getOrdineResponseDTO.getQuantita());
        getOrdineResponse.setPrezzoUnitario(getOrdineResponseDTO.getPrezzoUnitario());
        getOrdineResponse.setTotale(getOrdineResponseDTO.getTotale());
        getOrdineResponse.setStato(getOrdineResponseDTO.getStato());
        getOrdineResponse.setDataOrdine(getOrdineResponseDTO.getDataOrdine());
        getOrdineResponse.setHttpStatus(getOrdineResponseDTO.getHttpStatus());
        return getOrdineResponse;
    }

    public CreateOrdineResponseDTO entityToDTO(OrdineEntity ordineEntity) {
        CreateOrdineResponseDTO ordineResponseDTO = new CreateOrdineResponseDTO();

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

    public CreateOrdineResponse entityToResponse(OrdineEntity ordineEntity) {
        CreateOrdineResponse createOrdineResponse =  new CreateOrdineResponse();
        createOrdineResponse.setCodiceOrdine(ordineEntity.getCodiceOrdine());
        createOrdineResponse.setNomeCliente(ordineEntity.getNomeCliente());
        createOrdineResponse.setCognomeCliente(ordineEntity.getCognomeCliente());
        createOrdineResponse.setEmailCliente(ordineEntity.getEmailCliente());
        createOrdineResponse.setTelefonoCliente(ordineEntity.getTelefonoCliente());
        createOrdineResponse.setIndirizzoSpedizione(ordineEntity.getIndirizzoSpedizione());
        createOrdineResponse.setCittaSpedizione(ordineEntity.getCittaSpedizione());
        createOrdineResponse.setCap(ordineEntity.getCap());
        createOrdineResponse.setProdotto(ordineEntity.getProdotto());
        createOrdineResponse.setQuantita(ordineEntity.getQuantita());
        createOrdineResponse.setPrezzoUnitario(ordineEntity.getPrezzoUnitario());
        createOrdineResponse.setTotale(ordineEntity.getTotale());
        createOrdineResponse.setStato(ordineEntity.getStato());
        createOrdineResponse.setDataOrdine(ordineEntity.getDataOrdine());

        return createOrdineResponse;
    }

    public OrdineEntity responseDtoToEntity(CreateOrdineResponseDTO ordineResponseDTO) {
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

    public abstract ListOrdineResponse dtoToResponseList(ListOrdineResponseDTO listOrdineResponseDTO);
    public abstract ListOrdineResponseDTO listOrdineResponseToListOrdineResponseDTO(OrdineEntity ordineEntity);

}