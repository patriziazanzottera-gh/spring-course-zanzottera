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

    @Operation(summary = "Fornito uno stato, recupera la lista degli ordini")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"
                    , description = "OK"
                    , content = @Content(
                         mediaType = MediaType.APPLICATION_JSON_VALUE
                    , schema = @Schema(implementation = ListOrdineResponse.class)
                    , examples = @ExampleObject("""
                                        [
                           {
                             "cap": "20546",
                             "cittaSpedizione": "Settimo Milanese",
                             "codiceOrdine": "9MlM6RjvZ3nXFpGU331QDaiWC",
                             "cognomeCliente": "Bullone",
                             "dataOrdine": "2026-11-14T15:16:42.558965",
                             "emailCliente": "gaspare.bullone@teleport.com",
                             "httpStatus": null,
                             "id": 1,
                             "indirizzoSpedizione": "Via spuma, 54",
                             "nomeCliente": "Bibita",
                             "prezzoUnitario": 166.39,
                             "prodotto": "Bistecca al Sangiovese",
                             "quantita": 2,
                             "stato": "SPEDITO",
                             "telefonoCliente": "0237767",
                             "totale": 332.78
                           },
                           {
                             "cap": "20546",
                             "cittaSpedizione": "Paderno Dugnano",
                             "codiceOrdine": "yhSxFsbbDi6OQdSJ5GPqQaV5mOgz",
                             "cognomeCliente": "Bullone",
                             "dataOrdine": "2026-11-14T16:10:46.428251",
                             "emailCliente": "gaspare.zuzzurro@teleport.com",
                             "httpStatus": null,
                             "id": 2,
                             "indirizzoSpedizione": "Via abruzzi, 54",
                             "nomeCliente": "Zuzzurro",
                             "prezzoUnitario": 6.49,
                             "prodotto": "Olive all'ascolana",
                             "quantita": 72,
                             "stato": "SPEDITO",
                             "telefonoCliente": "0237767",
                             "totale": 467.28000000000003
                           },
                           {
                             "cap": "20946",
                             "cittaSpedizione": "Foligno",
                             "codiceOrdine": "OPcgvxNB1qDJa0sBNB3gKaIAtmZ2Fx",
                             "cognomeCliente": "Bullone",
                             "dataOrdine": "2026-11-21T11:47:37.540968",
                             "emailCliente": "gaspare.blue@teleport.com",
                             "httpStatus": null,
                             "id": 3,
                             "indirizzoSpedizione": "Via abruzzi, 54",
                             "nomeCliente": "Blue",
                             "prezzoUnitario": 9.99,
                             "prodotto": "Pappa al pomodoro",
                             "quantita": 6,
                             "stato": "SPEDITO",
                             "telefonoCliente": "0239967",
                             "totale": 59.94
                           },
                           {
                             "cap": "20946",
                             "cittaSpedizione": "Foligno",
                             "codiceOrdine": "KO06ZMRME3lC5pmDmcvjBhV2P",
                             "cognomeCliente": "Bullone",
                             "dataOrdine": "2026-11-21T11:59:06.363907",
                             "emailCliente": "gaspare.bluet@eleport.com",
                             "httpStatus": null,
                             "id": 4,
                             "indirizzoSpedizione": "Via abruzzi, 54",
                             "nomeCliente": "Blue",
                             "prezzoUnitario": 9.99,
                             "prodotto": "Pappa al pomodoro",
                             "quantita": 6,
                             "stato": "SPEDITO",
                             "telefonoCliente": null,
                             "totale": 59.94
                           }
                         ]
                         """)
            )),
            @ApiResponse(responseCode = "400"
                    , description = "NOT_FOUND"
                    , content = @Content(
                         mediaType = MediaType.APPLICATION_JSON_VALUE
                    , schema = @Schema(implementation = GetOrdineResponse.class)
                    , examples = @ExampleObject("""
                                {
                                  "appErrorCode": "Bad Request",
                                  "appErrorMessage": "Stato non ammesso",
                                  "httpStatusCode": "400 BAD_REQUEST",
                                  "path": "/api/ordini/",
                                  "timestamp": "2026-11-22T09:30:24.3609026"
                                }
                         """)
            )),
            @ApiResponse(responseCode = "200"
                    , description = "OK"
                    , content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    , schema = @Schema(implementation = GetOrdineResponse.class)
                    , examples = @ExampleObject("""
                                []
                         """)
            )),
            @ApiResponse(responseCode = "200"
                    , description = "OK"
                    , content = @Content(
                          mediaType = MediaType.APPLICATION_JSON_VALUE
                    , schema = @Schema(implementation = ListOrdineResponse.class)
                    , examples = @ExampleObject("""
                           [
                           {
                             "cap": "20546",
                             "cittaSpedizione": "Settimo Milanese",
                             "codiceOrdine": "9MlM6RjvZ3nXFpGU331QDaiWC",
                             "cognomeCliente": "Bullone",
                             "dataOrdine": "2026-11-14T15:16:42.558965",
                             "emailCliente": "gaspare.bullone@teleport.com",
                             "httpStatus": null,
                             "id": 1,
                             "indirizzoSpedizione": "Via spuma, 54",
                             "nomeCliente": "Bibita",
                             "prezzoUnitario": 166.39,
                             "prodotto": "Bistecca al Sangiovese",
                             "quantita": 2,
                             "stato": "IN_LAVORAZIONE",
                             "telefonoCliente": "0237767",
                             "totale": 332.78
                           },
                           {
                             "cap": "20546",
                             "cittaSpedizione": "Paderno Dugnano",
                             "codiceOrdine": "yhSxFsbbDi6OQdSJ5GPqQaV5mOgz",
                             "cognomeCliente": "Bullone",
                             "dataOrdine": "2026-11-14T16:10:46.428251",
                             "emailCliente": "gaspare.zuzzurro@teleport.com",
                             "httpStatus": null,
                             "id": 2,
                             "indirizzoSpedizione": "Via abruzzi, 54",
                             "nomeCliente": "Zuzzurro",
                             "prezzoUnitario": 6.49,
                             "prodotto": "Olive all'ascolana",
                             "quantita": 72,
                             "stato": "SPEDITO",
                             "telefonoCliente": "0237767",
                             "totale": 467.28000000000003
                           },
                           {
                             "cap": "20946",
                             "cittaSpedizione": "Foligno",
                             "codiceOrdine": "OPcgvxNB1qDJa0sBNB3gKaIAtmZ2Fx",
                             "cognomeCliente": "Bullone",
                             "dataOrdine": "2026-11-21T11:47:37.540968",
                             "emailCliente": "gaspare.blue@teleport.com",
                             "httpStatus": null,
                             "id": 3,
                             "indirizzoSpedizione": "Via abruzzi, 54",
                             "nomeCliente": "Blue",
                             "prezzoUnitario": 9.99,
                             "prodotto": "Pappa al pomodoro",
                             "quantita": 6,
                             "stato": "SPEDITO",
                             "telefonoCliente": "0239967",
                             "totale": 59.94
                           },
                           {
                             "cap": "20946",
                             "cittaSpedizione": "Foligno",
                             "codiceOrdine": "KO06ZMRME3lC5pmDmcvjBhV2P",
                             "cognomeCliente": "Bullone",
                             "dataOrdine": "2026-11-21T11:59:06.363907",
                             "emailCliente": "gaspare.bluet@eleport.com",
                             "httpStatus": null,
                             "id": 4,
                             "indirizzoSpedizione": "Via abruzzi, 54",
                             "nomeCliente": "Blue",
                             "prezzoUnitario": 9.99,
                             "prodotto": "Pappa al pomodoro",
                             "quantita": 6,
                             "stato": "SPEDITO",
                             "telefonoCliente": null,
                             "totale": 59.94
                           }
                         ]
                         """)
            ))
    })
    @GetMapping("/ordini/")
    public ResponseEntity<List<ListOrdineResponse>> getOrdiniByStato(@RequestParam("stato") String stato) {

        List<ListOrdineResponse> ordineResponseList = service.getByStato(stato)
                .getBody()
                .stream()
                .map(ordineMapper::dtoToResponseList)
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ordineResponseList);
    }

    @Operation(summary = "Modifica lo Stato di un ordine")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"
                    , description = "OK"
                    , content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    , schema = @Schema(implementation = PatchStatoResponse.class)
                    , examples = @ExampleObject("""
                        {
                         "cap": "20946",
                         "cittaSpedizione": "Foligno",
                         "codiceOrdine": "KO06ZMRME3lC5pmDmcvjBhV2P",
                         "cognomeCliente": "Bullone",
                         "dataOrdine": "2026-11-21T11:59:06.363907",
                         "emailCliente": "gaspare.bluet@eleport.com",
                         "httpStatus": "200 OK",
                         "id": 4,
                         "indirizzoSpedizione": "Via abruzzi, 54",
                         "nomeCliente": "Blue",
                         "prezzoUnitario": 9.99,
                         "prodotto": "Pappa al pomodoro",
                         "quantita": 6,
                         "stato": "IN_LAVORAZIONE",
                         "telefonoCliente": null,
                         "totale": 59.94
                       }
                    """)
            )),
            @ApiResponse(responseCode = "400"
                    , description = "BAD_REQUEST"
                    , content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE
                    , schema = @Schema(implementation = PatchStatoResponse.class)
                    , examples = @ExampleObject("""
                       {
                         "appErrorCode": "Bad Request",
                         "appErrorMessage": "Stato non ammesso",
                         "httpStatusCode": "400 BAD_REQUEST",
                         "path": "/api/ordini/4/stato",
                         "timestamp": "2026-11-21T12:42:30.9919171"
                       }
                    """)
            )),
            @ApiResponse(responseCode = "400"
                    , description = "BAD_REQUEST"
                    , content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE
                    , schema = @Schema(implementation = PatchStatoResponse.class)
                    , examples = @ExampleObject("""
                       {
                           "appErrorCode": "Not Found",
                           "appErrorMessage": "Ordine 6 non trovato ",
                           "httpStatusCode": "404 NOT_FOUND",
                           "path": "/api/ordini/6/stato",
                           "timestamp": "2026-11-22T09:47:29.8954498"
                       }
                    """)
            )),

            @ApiResponse(responseCode = "409"
                    , description = "CONFLICT"
                    , content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE
                    , schema = @Schema(implementation = PatchStatoResponse.class)
                    , examples = @ExampleObject("""
                        {
                              "cap": null,
                              "cittaSpedizione": null,
                              "codiceOrdine": null,
                              "cognomeCliente": null,
                              "dataOrdine": null,
                              "emailCliente": null,
                              "httpStatus": "409 CONFLICT",
                              "id": null,
                              "indirizzoSpedizione": null,
                              "nomeCliente": null,
                              "prezzoUnitario": null,
                              "prodotto": null,
                              "quantita": null,
                              "stato": null,
                              "telefonoCliente": null,
                              "totale": null
                            }
                    """)
            ))
    })
    @PatchMapping("/ordini/{id}/stato")
    public ResponseEntity<PatchStatoResponse> patchStato(@PathVariable("id") Long id, @RequestBody String stato) {

        PatchStatoResponseDTO patchStatoResponseDTO = service.patchStato(id, stato);
        PatchStatoResponse patchStatoResponse = ordineMapper.dtoToResponsePatch(patchStatoResponseDTO);
        return ResponseEntity.status(patchStatoResponse.getHttpStatus()).body(patchStatoResponse);
    }

    @Operation(summary = "Per ogni stato restituisce il numero di ordini")
    @ApiResponse(responseCode = "201"
            , description = "Created"
            , content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE
            , schema =  @Schema(implementation = CreateOrdineResponse.class)
            , examples = @ExampleObject("""
               [
                   {
                     "numeroOrdini": 4,
                     "stato": "SPEDITO"
                   },
                   {
                     "numeroOrdini": 1,
                     "stato": "RICEVUTO"
                   }
                 ]
            """)
    ))
    @GetMapping("/ordini/statistiche/stato")
    public ResponseEntity<List<StatisticheStatoResponse>> statisticheStato() {

        List<StatisticheStatoResponse> statisticheStatoResponse = service.statisticheStato().stream()
                .map(ordineMapper::dtoToResponseStatistiche)
                .collect(Collectors.toList());

        return ResponseEntity.ok(statisticheStatoResponse);
    }

}
