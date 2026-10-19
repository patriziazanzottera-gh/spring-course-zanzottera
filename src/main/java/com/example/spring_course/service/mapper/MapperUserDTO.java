package com.example.spring_course.service.mapper;

import ch.qos.logback.core.model.ComponentModel;
import com.example.spring_course.model.UserRequest;
import com.example.spring_course.service.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class MapperUserDTO {

    @Mapping(target = "indirizzo", expression = "java(mapIndirizzo(userRequest.getCitta(), userRequest.getNazione())")
    public abstract UserDTO toRequestDTO (UserRequest userRequest);

    private String mapIndirizzo(String citta, String nazione) {
        return citta.concat( " - " ).concat(nazione);
    }
}
