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
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
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
        CreateOrdineResponse ordineResponse = new CreateOrdineResponse(ordineEntity, HttpStatus.CREATED);

        return ordineMapper.responseToDTO(ordineResponse);
    }

    public GetOrdineResponseDTO getById(Long id) {

        OrdineEntity entity = ordineRepository.findById(id).orElseThrow(()->new AppException("Ordine " + id + " non trovato ", HttpStatus.NOT_FOUND));
        GetOrdineResponse getOrdineResponse = new GetOrdineResponse(entity, HttpStatus.OK);
        log.info("Ordine trovato {}",entity.getId());
        return ordineMapper.responseToDTOGet(getOrdineResponse);
    }

    public List<ListOrdineResponseDTO> getByStato(String statoStr) {

        List<OrdineEntity> entities = ordineRepository.findByStato(Stato.valueOf(statoStr));
        if (entities.isEmpty()) {
            entities = ordineRepository.findAll();
        }

        log.info("Trovato {}",entities.size() + " ordini");
        return entities.stream()
                .map(ordineMapper::listOrdineResponseToListOrdineResponseDTO)
                .collect(Collectors.toList());
    }

    public PatchStatoResponseDTO patchStato(@Positive Long id, @NotNull Stato stato) {

        PatchStatoResponse patchStatoResponse = null;
        if (stato.equals(Stato.IN_LAVORAZIONE) || stato.equals(Stato.SPEDITO) || stato.equals(Stato.ANNULLATO) ) {
            OrdineEntity entity = ordineRepository.findById(id).orElseThrow(()->new AppException("Ordine " + id + " non trovato ", HttpStatus.NOT_FOUND));
            if ( ((entity.getStato().equals(Stato.RICEVUTO) && stato.equals(Stato.IN_LAVORAZIONE))) ||
                    ((entity.getStato().equals(Stato.IN_LAVORAZIONE) && (stato.equals(Stato.SPEDITO) || stato.equals(Stato.ANNULLATO)))) ) {
                int updated= ordineRepository.patchStato(id, stato.name());
                if (updated>=1) {
                    patchStatoResponse = new PatchStatoResponse(entity, HttpStatus.OK);
                    log.info("Ordine trovato {}",entity.getId() + "Nuovo stato: " + stato);
                } else {
                    //l'id esiste, controllo già fatto, se mi restituisce 0 è andato male Ben Afflleck
                    patchStatoResponse = new PatchStatoResponse(new OrdineEntity(), HttpStatus.INTERNAL_SERVER_ERROR);
                }

            } else {
                patchStatoResponse = new PatchStatoResponse(new OrdineEntity(), HttpStatus.CONFLICT);
            }
        } else {
            patchStatoResponse = new PatchStatoResponse(new OrdineEntity(), HttpStatus.CONFLICT);
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