package com.example.spring_course.controller;

import com.example.spring_course.model.*;
import com.example.spring_course.repository.model.Stato;
import com.example.spring_course.service.OrdineService;
import com.example.spring_course.service.dto.CreateOrdineResponseDTO;
import com.example.spring_course.service.dto.GetOrdineResponseDTO;
import com.example.spring_course.service.dto.PatchStatoResponseDTO;
import com.example.spring_course.service.dto.StatisticheStatoResponseDTO;
import com.example.spring_course.service.mapper.OrdineMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Operation(summary = "Fornito un id, recupera un ordine")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"
                    , description = "OK"
                    , content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                                    , schema = @Schema(implementation = GetOrdineResponse.class)
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
                                                "prezzoUnitario":"2"
                                                "totale":"1200"
                                        }
                                    """)
            )),
            @ApiResponse(responseCode = "400"
                    , description = "NOT_FOUND"
                    , content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                                    , schema = @Schema(implementation = GetOrdineResponse.class)
                                    , examples = @ExampleObject("""
                                        {
                                           "appErrorCode": "Not Found",
                                           "appErrorMessage": "Ordine 1488 non trovato ",
                                           "httpStatusCode": "404 NOT_FOUND",
                                           "path": "/api/ordini/1488",
                                           "timestamp": "2026-11-21T11:45:57.3921928"
                                         }
                                    """)
            ))
    })
    @GetMapping("/ordini/{id}")
    public ResponseEntity<GetOrdineResponse> getOrdineById(@PathVariable("id") Long id) {
        GetOrdineResponseDTO getOrdineResponseDTO = service.getById(id);
        GetOrdineResponse getOrdineResponse = ordineMapper.dtoToResponseGet(getOrdineResponseDTO);

        return ResponseEntity.status(getOrdineResponse.getHttpStatus()).body(getOrdineResponse);
    }

    @GetMapping("/ordini/")
    public ResponseEntity<List<ListOrdineResponse>> getOrdiniByStato(@RequestParam("stato") String stato) {

        List<ListOrdineResponse> ordineResponseList = service.getByStato(stato)
                .stream()
                .map(ordineMapper::dtoToResponseList)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ordineResponseList);
    }

    @PatchMapping("/ordini/{id}/stato")
    public ResponseEntity<PatchStatoResponse> patchStato(@PathVariable("id") Long id, @RequestBody String stato) {

        PatchStatoResponseDTO patchStatoResponseDTO = service.patchStato(id, stato);
        PatchStatoResponse patchStatoResponse = ordineMapper.dtoToResponsePatch(patchStatoResponseDTO);
        return ResponseEntity.status(patchStatoResponse.getHttpStatus()).body(patchStatoResponse);
    }

    @GetMapping("/ordini/statistiche/stato")
    public ResponseEntity<List<StatisticheStatoResponse>> statisticheStato() {

        List<StatisticheStatoResponse> statisticheStatoResponse = service.statisticheStato().stream()
                .map(ordineMapper::dtoToResponseStatistiche)
                .collect(Collectors.toList());

        return ResponseEntity.ok(statisticheStatoResponse);
    }

}
