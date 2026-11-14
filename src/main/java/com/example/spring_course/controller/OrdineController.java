package com.example.spring_course.controller;

import com.example.spring_course.model.*;
import com.example.spring_course.repository.model.Stato;
import com.example.spring_course.service.OrdineService;
import com.example.spring_course.service.dto.CreateOrdineResponseDTO;
import com.example.spring_course.service.dto.GetOrdineResponseDTO;
import com.example.spring_course.service.dto.PatchStatoResponseDTO;
import com.example.spring_course.service.mapper.OrdineMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name="OrdineController", description = "Controller per CRUD sull' ordine")
public class OrdineController {

    private static final Logger logger = LoggerFactory.getLogger(OrdineController.class);
    private final OrdineMapper ordineMapper;
    private final OrdineService service;


    @Operation(summary = "crea un nuovo ordine")
    @ApiResponse(responseCode = "201"
            , description = "Created"
            , content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE
            , schema =  @Schema(implementation = CreateOrdineResponse.class)
            , examples = @ExampleObject("""
                {
                        "nomeCliente":"Tizio",
                        "cognomeCliente":"Caio",
                        "emailCliente":"tizio.caio@teleport.com",
                        "telefonoCliente":"0234567",
                        "indirizzoSpedizione":"Via dei Matti, 0",
                        "cittaSpedizione":"Canicatti",
                        "cap":"20014",
                        "prodotto":"Marmellata di lamponi",
                        "quantita":"600",
                        "prezzoUnitario":"1.39"
                }
            """)
    ))
    @PostMapping("/ordini")
    public ResponseEntity<CreateOrdineResponse> createOrdine(@Valid @RequestBody CreateOrdineRequest ordineRequest) {
        CreateOrdineResponseDTO ordineResponseDTO = service.createOrdine(ordineMapper.toRequestDTO(ordineRequest));
        CreateOrdineResponse userResponse = ordineMapper.dtoToResponse(ordineResponseDTO);
        return ResponseEntity.status(userResponse.getHttpStatus()).body(userResponse);
    }

    @GetMapping("/ordini/{id}")
    public ResponseEntity<GetOrdineResponse> getOrdineById(@PathVariable("id") Long id) {
        GetOrdineResponseDTO getOrdineResponseDTO = service.getById(id);
        GetOrdineResponse getOrdineResponse = ordineMapper.dtoToResponseGet(getOrdineResponseDTO);

        return ResponseEntity.status(getOrdineResponse.getHttpStatus()).body(getOrdineResponse);
    }

    @GetMapping("/ordini/")
    public ResponseEntity<List<ListOrdineResponse>> getOrdiniByStato(@RequestParam("stato") String stato) {

        List<ListOrdineResponse> ordineResponseListDTO = service.getByStato(stato)
                .stream()
                .map(ordineMapper::dtoToResponseList)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ordineResponseListDTO);
    }

    @PatchMapping("/ordini/{id}/stato")
    public ResponseEntity<PatchStatoResponse> patchStato(@PathVariable("id") Long id, @RequestBody Stato stato) {

        PatchStatoResponseDTO patchStatoResponseDTO = service.patchStato(id, stato);
        PatchStatoResponse patchStatoResponse = ordineMapper.dtoToResponsePatch(patchStatoResponseDTO);
        return ResponseEntity.status(patchStatoResponse.getHttpStatus()).body(patchStatoResponse);
    }
}
