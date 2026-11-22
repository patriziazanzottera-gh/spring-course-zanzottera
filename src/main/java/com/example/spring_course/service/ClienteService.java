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
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.time.LocalDateTime;

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
/*
    public ResponseEntity<List<ListOrdineResponseDTO>> getByStato(String statoStr) {
        Stato stato = null;
        List<OrdineEntity> entities = null;
        //se lo stato non viene precisato resituisco tutto
        if (statoStr == null || statoStr.isEmpty()) {
            entities = ordineRepository.findAll();
        } else {
            try {
                stato = Stato.valueOf(statoStr);
            } catch (IllegalArgumentException e) {
                throw new AppException("Stato non ammesso", HttpStatus.BAD_REQUEST);
            }
            entities = ordineRepository.findByStato(stato);
        }

        if (entities.isEmpty()) {
            log.info("Nessun Ordine trovato per questo stato {}", statoStr);
            ResponseEntity.ok(Collections.emptyList());
        }

        log.info("Trovato {}",entities.size() + " ordini");
        return ResponseEntity.ok(entities.stream()
                .map(ordineMapper::listOrdineResponseToListOrdineResponseDTO)
                .collect(Collectors.toList()));
    }

    public PatchStatoResponseDTO patchStato(@Positive Long id, @NotNull String stato) {

        PatchStatoResponse patchStatoResponse = null;
        try {
            Stato.valueOf(stato);
        } catch (IllegalArgumentException e) {
            throw new AppException("Stato non ammesso", HttpStatus.BAD_REQUEST);
        }
        if (stato.equals(Stato.IN_LAVORAZIONE.name()) || stato.equals(Stato.SPEDITO.name()) || stato.equals(Stato.ANNULLATO.name()) ) {
            OrdineEntity entity = ordineRepository.findById(id).orElseThrow(()->new AppException("Ordine " + id + " non trovato ", HttpStatus.NOT_FOUND));
            if ( ((entity.getStato().equals(Stato.RICEVUTO) && stato.equals(Stato.IN_LAVORAZIONE.name()))) ||
                    ((entity.getStato().equals(Stato.IN_LAVORAZIONE)) && (stato.equals(Stato.SPEDITO.name()) || stato.equals(Stato.ANNULLATO.name()))) ) {
                int updated= ordineRepository.patchStato(id, stato);
                if (updated>=1) {
                    patchStatoResponse = new PatchStatoResponse(entity, Stato.valueOf(stato));
                    log.info("Ordine trovato {}",entity.getId() + "Nuovo stato: " + stato);
                } else {
                    //l'id esiste, controllo già fatto, se mi restituisce 0 è andato male
                    throw new AppException("Ordine " + id + " non modificato, errore interno ", HttpStatus.INTERNAL_SERVER_ERROR);
                }

            } else {
                throw new AppException("Ordine " + id + " non modificato, stato non ammesso ", HttpStatus.CONFLICT);
            }
        } else {
            throw new AppException("Ordine " + id + " non modificato, stato non esistente ", HttpStatus.CONFLICT);
        }
        return ordineMapper.responseToDTOPatch(patchStatoResponse);
    }

    public List<StatisticheStatoResponseDTO> statisticheStato() {

        return ordineRepository.findNumeroOrdiniByStato()
                .stream()
                .map(s -> new StatisticheStatoResponseDTO(
                        s.getNumeroOrdini(),
                        s.getStato()
                ))
                .collect(Collectors.toList());
    }*/
}