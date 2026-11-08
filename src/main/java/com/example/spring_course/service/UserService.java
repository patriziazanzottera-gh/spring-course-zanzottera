package com.example.spring_course.service;

import com.example.spring_course.exception.AppException;
import com.example.spring_course.model.UserResponse;
import com.example.spring_course.repository.UsersRepository;
import com.example.spring_course.repository.model.UserEntity;
import com.example.spring_course.service.dto.UserRequestDTO;
import com.example.spring_course.service.dto.UserResponseDTO;
import com.example.spring_course.service.mapper.MapperUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;
    private final MapperUserDTO mapperUserDTO;

    public UserResponseDTO getUserResponse(UserRequestDTO user) {
        UUID oid = UUID.randomUUID();
        UserResponse userResponse = new UserResponse("User restituito con successo " + oid, oid.toString(), user, HttpStatus.OK);
        return mapperUserDTO.responseToDTO(userResponse);
    }

    public UserResponseDTO createUser_old(UserRequestDTO userDto) {

        UserResponse userResponse = null;
        Optional<UserEntity> optionalUserEntity = usersRepository.findByEmail(userDto.getEmail());

        if (optionalUserEntity.isEmpty()) {
            UserEntity userEntity = mapperUserDTO.dtoToEntity(userDto);
            usersRepository.save(userEntity);
            userResponse = new UserResponse("User creato con successo " + userEntity.getId(), userEntity.getId().toString(), userDto, HttpStatus.OK);
        } else {
            userResponse = new UserResponse("User già esistente", HttpStatus.FOUND);
        }
        return mapperUserDTO.responseToDTO(userResponse);
    }

    public UserResponseDTO createUser(UserRequestDTO userDto) {
        usersRepository.findByEmail(userDto.getEmail()).ifPresent(user -> {
            throw new AppException(
                    "User " + userDto.getEmail() + " già presente",
                    HttpStatus.CONFLICT
            );
        });

        UserEntity userEntity = mapperUserDTO.dtoToEntity(userDto);
        usersRepository.save(userEntity);
        UserResponse userResponse = new UserResponse("User creato", userEntity.getId().toString(), userDto, HttpStatus.OK);

        return mapperUserDTO.responseToDTO(userResponse);
    }

    public UserResponseDTO updateUser_old(UserRequestDTO userDto) {

        UserResponse userResponse = null;

        Optional<UserEntity> optionalUserEntity = usersRepository.findByEmail(userDto.getEmail());

        if (optionalUserEntity.isPresent()) {
            optionalUserEntity.get().setIndirizzo(userDto.getIndirizzo());
            optionalUserEntity.get().setNome(userDto.getNome());
            optionalUserEntity.get().setEta(userDto.getEta());
            optionalUserEntity.get().setEmail(userDto.getEmail());
            usersRepository.save(optionalUserEntity.get());
            userResponse = new UserResponse("User aggiornato con successo " + optionalUserEntity.get().getId(), optionalUserEntity.get().getId().toString(), userDto, HttpStatus.OK);
        } else {
            userResponse = new UserResponse("User non esistente", HttpStatus.NOT_FOUND);
        }
        return mapperUserDTO.responseToDTO(userResponse);
    }

    public UserResponseDTO updateUser(UserRequestDTO userDto) {

        UserEntity userEntity = usersRepository.findByEmail(userDto.getEmail()).orElseThrow(()->new AppException("User " + userDto.getEmail() + " non trovato ", HttpStatus.NOT_FOUND));

        userEntity.setIndirizzo(userDto.getIndirizzo());
        userEntity.setNome(userDto.getNome());
        userEntity.setEta(userDto.getEta());
        userEntity.setEmail(userDto.getEmail());
        usersRepository.save(userEntity);
        UserResponse userResponse = new UserResponse("User aggiornato con successo " + userEntity.getId(), userEntity.getId().toString(), userDto, HttpStatus.OK);

        return mapperUserDTO.responseToDTO(userResponse);
    }

    public UserResponseDTO getByName_old(String name) {
        UserResponse userResponse = null;
        List<UserEntity> userEntities = usersRepository.searchByName(name);
        if (userEntities == null || userEntities.isEmpty()) {
            userResponse = new UserResponse("Nessuno user trovato", HttpStatus.NOT_FOUND);
        } else {
            UserEntity userEntity = userEntities.getFirst();
            userResponse = mapperUserDTO.entityToResponse(userEntity);
            userResponse.setMessage("Utente trovato " + userEntity.getId());
            userResponse.setStatus(HttpStatus.OK);
        }

        return mapperUserDTO.responseToDTO(userResponse);
    }

    public UserResponseDTO getByName(String name) {
        UserResponse userResponse = null;
        List<UserEntity> userEntities = usersRepository.searchByName(name);
        if (userEntities == null || userEntities.isEmpty()) {
            throw new AppException("User " + name + " non trovato ", HttpStatus.NOT_FOUND);
        } else {
            UserEntity userEntity = userEntities.getFirst();
            userResponse = mapperUserDTO.entityToResponse(userEntity);
            userResponse.setMessage("Utente trovato " + userEntity.getId());
            userResponse.setStatus(HttpStatus.OK);
        }

        return mapperUserDTO.responseToDTO(userResponse);
    }

    public UserResponseDTO getById(Long oid) {

        UserEntity entity = usersRepository.findById(oid).orElseThrow(()->new AppException("User " + oid + " non trovato ", HttpStatus.NOT_FOUND));
        UserResponse userResponse = new UserResponse("Utente trovato " + entity.getId(), HttpStatus.OK);

        return mapperUserDTO.responseToDTO(userResponse);
    }

    public UserResponseDTO getByNameNative(String name) {
        UserResponse userResponse = null;
        List<UserEntity> userEntities = usersRepository.searchByNameNative(name);
        if (userEntities == null || userEntities.isEmpty()) {
            userResponse = new UserResponse("Nessuno user trovato", HttpStatus.NOT_FOUND);

        } else {
            UserEntity userEntity = userEntities.getFirst();
            userResponse = mapperUserDTO.entityToResponse(userEntity);
            userResponse.setMessage("Utente trovato " + userEntity.getId());
            userResponse.setStatus(HttpStatus.OK);
        }
        return mapperUserDTO.responseToDTO(userResponse);
    }
}