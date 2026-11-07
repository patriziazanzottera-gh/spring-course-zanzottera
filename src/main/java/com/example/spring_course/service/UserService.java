package com.example.spring_course.service;

import com.example.spring_course.exception.AppException;
import com.example.spring_course.model.UserResponse;
import com.example.spring_course.repository.UsersRepository;
import com.example.spring_course.repository.model.UserEntity;
import com.example.spring_course.service.dto.UserRequestDTO;
import com.example.spring_course.service.mapper.MapperUserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;
    private final MapperUserDTO mapperUserDTO;

    public ResponseEntity<UserResponse> geUserResponse(UserRequestDTO user) {
        UUID oid = UUID.randomUUID();
        UserResponse userResponse = new UserResponse("User creato con successo " + oid, oid.toString(), user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    public ResponseEntity<UserResponse> createUser(UserRequestDTO userDto) {
        UUID oid = UUID.randomUUID();
        UserResponse userResponse = null;

        Optional<UserEntity> optionalUserEntity = usersRepository.findByEmail(userDto.getEmail());

        if (optionalUserEntity.isEmpty()) {
            log.info("Il findByEmail {} non ha restituito risultati - creo l'utente", userDto.getEmail());
            UserEntity userEntity = mapperUserDTO.dtoToEntity(userDto);
            usersRepository.save(userEntity);
            log.info ("Utente creato " + userEntity.getNome());
            userResponse = new UserResponse("User creato con successo " + oid, oid.toString(), userDto);
        } else {
            log.error("User già esistente");
            userResponse = new UserResponse("User già esistente");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    public ResponseEntity<UserResponse> updateUser(UserRequestDTO userDto) {
        UUID oid = UUID.randomUUID();
        UserResponse userResponse = null;

        Optional<UserEntity> optionalUserEntity = usersRepository.findByEmail(userDto.getEmail());

        if (optionalUserEntity.isPresent()) {
            log.info("Il findByEmail {} ha restituito risultati - modifico l'utente", userDto.getEmail());
            optionalUserEntity.get().setIndirizzo(userDto.getIndirizzo());
            optionalUserEntity.get().setNome(userDto.getNome());
            optionalUserEntity.get().setEta(userDto.getEta());
            optionalUserEntity.get().setEmail(userDto.getEmail());
            usersRepository.save(optionalUserEntity.get());
            userResponse = new UserResponse("User aggiornato con successo " + oid, oid.toString(), userDto);
        } else {
            log.error("User non trovato");
            userResponse = new UserResponse("User non esistente");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    public ResponseEntity<UserResponse> getByName(String name) {
        UserResponse userResponse = null;
        List<UserEntity> userEntities = usersRepository.searchByName(name);
        if (userEntities == null || userEntities.isEmpty()) {
            userResponse = new UserResponse("Nessuno user trovato");
            log.error("User non trovato {} ", name);
        } else {
            userResponse = new UserResponse("Utente trovato " + userEntities.getFirst().getId());
            log.info("Restituito utente {}", userEntities.getFirst().getId());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    public ResponseEntity<UserResponse> getById(Long oid) {

        UserEntity entity = usersRepository.findById(oid).orElseThrow(()->new AppException("User " + oid + " non trovato ", HttpStatus.NOT_FOUND));
        UserResponse userResponse = new UserResponse("Utente trovato " + entity.getId());
        log.info("Restituito utente {}", entity.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    public ResponseEntity<UserResponse> getByNameNative(String name) {
        UserResponse userResponse = null;
        List<UserEntity> userEntities = usersRepository.searchByNameNative(name);
        if (userEntities == null || userEntities.isEmpty()) {
            userResponse = new UserResponse("Nessuno user trovato");
            log.error("User non trovato {} ", name);
        } else {
            userResponse = new UserResponse("Utente trovato " + userEntities.getFirst().getId());
            log.info("Restituito utente {}", userResponse.getUserId());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }
}
