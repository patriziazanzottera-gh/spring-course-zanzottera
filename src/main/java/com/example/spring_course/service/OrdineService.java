package com.example.spring_course.service;

import com.example.spring_course.exception.AppException;
import com.example.spring_course.model.CreateOrdineResponse;
import com.example.spring_course.model.GetOrdineResponse;
import com.example.spring_course.model.PatchStatoResponse;
import com.example.spring_course.repository.OrdineRepository;
import com.example.spring_course.repository.model.OrdineEntity;
import com.example.spring_course.repository.model.Stato;
import com.example.spring_course.service.dto.*;
import com.example.spring_course.service.mapper.OrdineMapper;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class OrdineService {

    private final OrdineRepository ordineRepository;
    private final OrdineMapper ordineMapper;

    private static String buildCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        int length = 20 + random.nextInt(11);
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public CreateOrdineResponseDTO createOrdine(CreateOrdineRequestDTO ordineRequestDTO) {
        ordineRepository.findByCodiceOrdine(ordineRequestDTO.getCodiceOrdine()).ifPresent(ordine -> {
            log.error("Ordine già esistente {}",ordineRequestDTO.getCodiceOrdine());
            throw new AppException("Ordine " + ordineRequestDTO.getCodiceOrdine() + " già presente",
                    HttpStatus.CONFLICT
            );
        });

        OrdineEntity ordineEntity = ordineMapper.dtoToEntity(ordineRequestDTO);
        ordineEntity.setCodiceOrdine(buildCode());
        ordineEntity.setStato(Stato.RICEVUTO);
        ordineEntity.setDataOrdine(LocalDateTime.now());

        ordineEntity = ordineRepository.save(ordineEntity);
        log.info("Nessun ordine trovato, lo creo {}",ordineEntity.getId());
        CreateOrdineResponse ordineResponse = new CreateOrdineResponse(ordineEntity);

        return ordineMapper.responseToDTO(ordineResponse);
    }

    public GetOrdineResponseDTO getById(Long id) {

        OrdineEntity entity = ordineRepository.findById(id).orElseThrow(()->new AppException("Ordine " + id + " non trovato ", HttpStatus.NOT_FOUND));
        GetOrdineResponse getOrdineResponse = new GetOrdineResponse(entity, HttpStatus.OK);
        log.info("Ordine trovato {}",entity.getId());
        return ordineMapper.responseToDTOGet(getOrdineResponse);
    }

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
    }
}