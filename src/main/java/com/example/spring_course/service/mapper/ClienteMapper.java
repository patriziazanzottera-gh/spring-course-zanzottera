package com.example.spring_course.service.mapper;

import com.example.spring_course.model.*;
import com.example.spring_course.repository.model.ClienteEntity;
import com.example.spring_course.service.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ClienteMapper {

    public abstract CreateClienteRequestDTO toRequestDTO (CreateClienteRequest clienteRequest);
    public abstract CreateClienteResponse dtoToResponse (CreateClienteResponseDTO clienteResponseDTO);
    public abstract GetClienteResponse dtoToGetResponse (GetClienteResponseDTO clienteResponseDTO);
    public abstract ClienteEntity dtoToEntity (CreateClienteRequestDTO clienteRequestDTO);
    public abstract CreateClienteResponseDTO entityToDTO (ClienteEntity clienteEntity);
    public abstract GetClienteResponseDTO entityToDTOGet (ClienteEntity clientEntity);

}