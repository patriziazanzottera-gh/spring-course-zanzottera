package com.example.spring_course.service;

import com.example.spring_course.exception.AppException;
import com.example.spring_course.model.CreateClienteResponse;
import com.example.spring_course.model.GetClienteResponse;
import com.example.spring_course.repository.ClienteRepository;
import com.example.spring_course.repository.model.ClienteEntity;
import com.example.spring_course.service.dto.*;
import com.example.spring_course.service.mapper.ClienteMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public CreateClienteResponseDTO createCliente(CreateClienteRequestDTO clienteRequestDTO) {
        clienteRepository.findByEmail(clienteRequestDTO.getEmail()).ifPresent(cliente -> {
            log.error("Cliente già esistente {}",clienteRequestDTO.getEmail());
            throw new AppException("Cliente " + clienteRequestDTO.getEmail() + " già presente",
                    HttpStatus.CONFLICT
            );
        });

        ClienteEntity clienteEntity = clienteMapper.dtoToEntity(clienteRequestDTO);
        clienteRepository.save(clienteEntity);
        log.info("Nessun ordine trovato, lo creo {}",clienteEntity.getId());
        return clienteMapper.entityToDTO(clienteEntity);
    }

    public GetClienteResponseDTO getById(Long id) {

        ClienteEntity entity = clienteRepository.findById(id).orElseThrow(()->new AppException("Cliente " + id + " non trovato ", HttpStatus.NOT_FOUND));
        log.info("Cliente trovato {}",entity.getId());
        return clienteMapper.entityToDTOGet(entity);
    }

    public ResponseEntity<List<ListClienteResponseDTO>> getAll() {

        List<ClienteEntity> entities = clienteRepository.findAll();;

        if (entities.isEmpty()) {
            log.info("Nessun Ordine trovato ");
            ResponseEntity.ok(Collections.emptyList());
        }

        log.info("Trovati {}",entities.size() + " clienti");
        return ResponseEntity.ok(entities.stream()
                .map(clienteMapper::listClienteResponseToListClienteResponseDTO)
                .collect(Collectors.toList()));
    }

}