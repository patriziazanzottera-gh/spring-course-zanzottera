package com.example.spring_course.service.mapper;

import com.example.spring_course.model.UserRequest;
import com.example.spring_course.model.UserResponse;
import com.example.spring_course.repository.model.UserEntity;
import com.example.spring_course.service.dto.UserRequestDTO;
import com.example.spring_course.service.dto.UserResponseDTO;
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

    public UserResponseDTO entityToDTO(UserResponse userResponse) {
        UserResponseDTO userDTO = new UserResponseDTO();
        userDTO.setEmail(userResponse.getEmail());
        userDTO.setIndirizzo(userResponse.getIndirizzo());
        userDTO.setNome(userResponse.getNome());
        userDTO.setEta(userResponse.getEta());

        return userDTO;
    }

    public UserEntity responseDtoToEntity(UserResponseDTO userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userDto.getEmail());
        userEntity.setIndirizzo(userDto.getIndirizzo());
        userEntity.setNome(userDto.getNome());
        userEntity.setEta(userDto.getEta());

        return userEntity;
    }
}
