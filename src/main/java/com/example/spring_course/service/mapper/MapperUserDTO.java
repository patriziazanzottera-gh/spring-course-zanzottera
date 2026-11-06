package com.example.spring_course.service.mapper;

import com.example.spring_course.model.UserRequest;
import com.example.spring_course.model.UserResponse;
import com.example.spring_course.repository.model.UserEntity;
import com.example.spring_course.service.dto.UserRequestDTO;
import org.hibernate.mapping.UserDefinedType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class MapperUserDTO {

    @Mapping(target = "indirizzo", expression = "java(mapIndirizzo(userRequest.getCitta(), userRequest.getNazione()))")
    public abstract UserRequestDTO toRequestDTO (UserRequest userRequest);

    public UserEntity map(UserRequestDTO userDTO) {
        UserEntity userEntity = new UserEntity();

        return userEntity;
    }

    public String mapIndirizzo(String citta, String nazione) {
        return citta.concat( " - " ).concat(nazione);
    }

    public UserEntity dtoToEntity(UserRequestDTO userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userDto.getEmail());
        userEntity.setIndirizzo(userDto.getIndirizzo());
        userEntity.setNome(userDto.getNome());
        userEntity.setEta(userDto.getEta());

        return userEntity;
    }


    public UserResponse fromDtoToResponse(UserRequestDTO userRequestDTO) {
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(userRequestDTO.getEmail());
        userResponse.setIndirizzo(userRequestDTO.getIndirizzo());
        userResponse.setNome(userRequestDTO.getNome());
        userResponse.setEta(userRequestDTO.getEta());
        return userResponse;
    }
}
