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

    public UserResponseDTO responseToDTO(UserResponse userResponse) {
        UserResponseDTO userDTO = new UserResponseDTO();
        userDTO.setEmail(userResponse.getEmail());
        userDTO.setIndirizzo(userResponse.getIndirizzo());
        userDTO.setNome(userResponse.getNome());
        userDTO.setEta(userResponse.getEta());
        userDTO.setStatus(userResponse.getStatus());
        userDTO.setMessage(userResponse.getMessage());
        return userDTO;
    }

    public UserResponse dtoToResponse(UserResponseDTO userResponseDTO) {
        UserResponse user = new UserResponse();
        user.setEmail(userResponseDTO.getEmail());
        user.setIndirizzo(userResponseDTO.getIndirizzo());
        user.setNome(userResponseDTO.getNome());
        user.setEta(userResponseDTO.getEta());
        user.setStatus(userResponseDTO.getStatus());
        user.setMessage(userResponseDTO.getMessage());
        return user;
    }

    public UserResponseDTO entityToDTO(UserEntity userEntity) {
        UserResponseDTO userDTO = new UserResponseDTO();
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setIndirizzo(userEntity.getIndirizzo());
        userDTO.setNome(userEntity.getNome());
        userDTO.setEta(userEntity.getEta());

        return userDTO;
    }

    public UserResponse entityToResponse(UserEntity userEntity) {
        UserResponse user =  new UserResponse();
        user.setEmail(userEntity.getEmail());
        user.setIndirizzo(userEntity.getIndirizzo());
        user.setNome(userEntity.getNome());
        user.setEta(userEntity.getEta());

        return user;
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