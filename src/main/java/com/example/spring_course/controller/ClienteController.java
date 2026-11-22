package com.example.spring_course.controller;

import com.example.spring_course.model.*;
import com.example.spring_course.service.ClienteService;
import com.example.spring_course.service.dto.CreateClienteResponseDTO;
import com.example.spring_course.service.dto.GetClienteResponseDTO;
import com.example.spring_course.service.mapper.ClienteMapper;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name="ClienteController", description = "Controller per CRUD sul cliente")
public class ClienteController {

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);
    private final ClienteMapper clienteMapper;
    private final ClienteService service;

    @Operation(summary = "Registra un cliente")
    @ApiResponse(responseCode = "201"
            , description = "Created"
            , content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE
            , schema =  @Schema(implementation = CreateClienteResponse.class)
            , examples = @ExampleObject("""
                {
                        "nome":"Tizio",
                        "cognome":"Caio",
                        "email":"tizio.caio@teleport.com",
                        "telefono":"0234567",
                        "indirizzo":"Via dei Matti, 0",
                        "citta":"Canicatti",
                        "cap":"20014"
                }
            """)
    ))
    @PostMapping("/clienti")
    public ResponseEntity<CreateClienteResponse> createCliente(@Valid @RequestBody CreateClienteRequest clienteRequest) {
        CreateClienteResponseDTO clienteResponseDTO = service.createCliente(clienteMapper.toRequestDTO(clienteRequest));
        CreateClienteResponse clienteResponse = clienteMapper.dtoToResponse(clienteResponseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteResponse);
    }

    @Operation(summary = "Recupera un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"
                    , description = "OK"
                    , content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                                    , schema = @Schema(implementation = GetClienteResponse.class)
                                    , examples = @ExampleObject("""
                                        {
                                               "id": 5,
                                               "nome": "Tizio",
                                               "cognome": "Rosa",
                                               "email": "tizio.rosa@teleport.com",
                                               "telefono": "0234567",
                                               "indirizzo": "Via dei Noiosi, 0",
                                               "citta": "Pisa",
                                               "cap": "20317"
                                        }
                                    """)
            )),
            @ApiResponse(responseCode = "400"
                    , description = "NOT_FOUND"
                    , content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                                    , schema = @Schema(implementation = GetClienteResponse.class)
                                    , examples = @ExampleObject("""
                                        {
                                           "appErrorCode": "Not Found",
                                           "appErrorMessage": "Cliente tizio.caio@gmail.it non trovato ",
                                           "httpStatusCode": "404 NOT_FOUND",
                                           "path": "/api/clienti/1488",
                                           "timestamp": "2026-11-21T11:45:57.3921928"
                                         }
                                    """)
            ))
    })
    @GetMapping("/clienti/{id}")
    public ResponseEntity<GetClienteResponse> getClienteById(@PathVariable("id") Long id) {
        GetClienteResponseDTO getClienteResponseDTO = service.getById(id);
        GetClienteResponse getClienteResponse = clienteMapper.dtoToGetResponse(getClienteResponseDTO);

        return ResponseEntity.status(HttpStatus.OK).body(getClienteResponse);
    }

    @Operation(summary = "Elenca i clienti")
    @ApiResponse(responseCode = "200"
            , description = "OK"
            , content = @Content(
                 mediaType = MediaType.APPLICATION_JSON_VALUE
            , schema = @Schema(implementation = ListOrdineResponse.class)
            , examples = @ExampleObject("""
                [
                   {
                      "id": 1,
                      "nome": "Tizio",
                      "cognome": "Rosa",
                      "email": "tizio.rosa@teleport.com",
                      "telefono": "0234567",
                      "indirizzo": "Via dei Noiosi, 0",
                      "citta": "Pisa",
                      "cap": "20317"
                   },
                   {
                     "id": 2,
                      "nome": "Tizio",
                      "cognome": "Blu",
                      "email": "tizio.blu@teleport.com",
                      "telefono": "0234567",
                      "indirizzo": "Via dei Pericolosi, 0",
                      "citta": "Como",
                      "cap": "20307"
                   }
                ]
             """)
            )
    )
    @GetMapping("/clienti/")
    public ResponseEntity<List<ListClienteResponse>> getAllClienti() {

        List<ListClienteResponse> clienteResponseList = service.getAll()
                .getBody()
                .stream()
                .map(clienteMapper::dtoToResponseList)
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clienteResponseList);
    }

}
