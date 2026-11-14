package com.example.spring_course.service.mapper;

import com.example.spring_course.model.*;
import com.example.spring_course.repository.model.OrdineEntity;
import com.example.spring_course.service.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class OrdineMapper {

    @Mapping(target = "totale", expression = "java(calcolaTotale(ordineRequest.getPrezzoUnitario(), ordineRequest.getQuantita()))")
    public abstract CreateOrdineRequestDTO toRequestDTO (CreateOrdineRequest ordineRequest);

    public Double calcolaTotale(Double prezzoUnitario, Integer quantita) {
        return prezzoUnitario*quantita;
    }

    public abstract OrdineEntity dtoToEntity(CreateOrdineRequestDTO ordineRequestDTO);
    public abstract CreateOrdineResponseDTO responseToDTO(CreateOrdineResponse createOrdineResponse);
    public abstract GetOrdineResponseDTO responseToDTOGet(GetOrdineResponse getOrdineResponse);
    public abstract CreateOrdineResponse dtoToResponse(CreateOrdineResponseDTO ordineResponseDTO);
    public abstract GetOrdineResponse dtoToResponseGet(GetOrdineResponseDTO listOrdineResponseDTO);
    public abstract CreateOrdineResponseDTO entityToDTO(OrdineEntity listOrdineResponseDTO);
    public abstract CreateOrdineResponse entityToResponse(OrdineEntity listOrdineResponseDTO);
    public abstract OrdineEntity responseDtoToEntity(CreateOrdineResponseDTO listOrdineResponseDTO);
    public abstract ListOrdineResponse dtoToResponseList(ListOrdineResponseDTO listOrdineResponseDTO);
    public abstract PatchStatoResponse dtoToResponsePatch(PatchStatoResponseDTO patchOrdineResponseDTO);
    public abstract ListOrdineResponseDTO listOrdineResponseToListOrdineResponseDTO(OrdineEntity ordineEntity);
    public abstract PatchStatoResponseDTO responseToDTOPatch(PatchStatoResponse patchOrdineResponse);
    public abstract StatisticheStatoResponse dtoToResponseStatistiche(StatisticheStatoResponseDTO statisticheStatoResponseDTO);
}