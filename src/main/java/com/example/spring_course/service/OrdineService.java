package com.example.spring_course.service;

import com.example.spring_course.exception.AppException;
import com.example.spring_course.model.OrdineResponse;
import com.example.spring_course.repository.OrdineRepository;
import com.example.spring_course.repository.model.OrdineEntity;
import com.example.spring_course.service.dto.OrdineRequestDTO;
import com.example.spring_course.service.dto.OrdineResponseDTO;
import com.example.spring_course.service.mapper.OrdineMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrdineService {

    private final OrdineRepository ordineRepository;
    private final OrdineMapper ordineMapper;

    public OrdineResponseDTO createOrdine(OrdineRequestDTO ordineRequestDTO) {
        ordineRepository.findByCodiceOrdine(ordineRequestDTO.getCodiceOrdine()).ifPresent(ordine -> {
            log.error("Ordine già esistente {}",ordineRequestDTO.getCodiceOrdine());
            throw new AppException("Ordine " + ordineRequestDTO.getCodiceOrdine() + " già presente",
                    HttpStatus.CONFLICT
            );
        });

        OrdineEntity ordineEntity = ordineMapper.dtoToEntity(ordineRequestDTO);
        ordineRepository.save(ordineEntity);
        log.info("Nessun utente trovato, lo creo {}",ordineEntity.getId());
        OrdineResponse userResponse = new OrdineResponse("Ordine creato", ordineEntity.getId(), ordineRequestDTO, HttpStatus.OK);

        return ordineMapper.responseToDTO(userResponse);
    }

}