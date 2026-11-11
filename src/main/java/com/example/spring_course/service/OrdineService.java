package com.example.spring_course.service;

import com.example.spring_course.exception.AppException;
import com.example.spring_course.model.CreateOrdineResponse;
import com.example.spring_course.model.GetOrdineResponse;
import com.example.spring_course.repository.OrdineRepository;
import com.example.spring_course.repository.model.OrdineEntity;
import com.example.spring_course.repository.model.Stato;
import com.example.spring_course.service.dto.CreateOrdineRequestDTO;
import com.example.spring_course.service.dto.CreateOrdineResponseDTO;
import com.example.spring_course.service.dto.GetOrdineResponseDTO;
import com.example.spring_course.service.mapper.OrdineMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

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

    public GetOrdineResponseDTO getById(Long oid) {

        OrdineEntity entity = ordineRepository.findById(oid).orElseThrow(()->new AppException("Ordine " + oid + " non trovato ", HttpStatus.NOT_FOUND));
        GetOrdineResponse getOrdineResponse = new GetOrdineResponse(entity, HttpStatus.OK);
        log.info("Ordine trovato {}",entity.getId());
        return ordineMapper.responseToDTOGet(getOrdineResponse);
    }

    public List<ListOrdineResponseDTO> getByStato(String stato) {

        List<OrdineEntity> entities = ordineRepository.findByStato(stato).orElseThrow(()->new AppException("Nessun ordine trovato ", HttpStatus.NOT_FOUND));
        ListOrdineResponse listOrdineResponse = new ListOrdineResponse(entities, HttpStatus.OK);
        log.info("Trovato {}",entities.size() + " ordini");
        return ordineMapper.responseToDTOList(listOrdineResponse);
    }

}