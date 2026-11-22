package com.example.spring_course.service.mapper;

import com.example.spring_course.model.*;
import com.example.spring_course.repository.model.OrdineEntity;
import com.example.spring_course.service.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class RigaOrdineMapper {

    @Mapping(target = "totaleRiga", expression = "java(calcolaTotale(rigaOrdineRequest.getPrezzoUnitario(), rigaOrdineRequest.getQuantita()))")
    public abstract CreateRigaOrdineRequestDTO toRequestDTO (CreateRigaOrdineRequest rigaOrdineRequest);

    public Double calcolaTotale(Double prezzoUnitario, Integer quantita) {
        return prezzoUnitario*quantita;
    }
}