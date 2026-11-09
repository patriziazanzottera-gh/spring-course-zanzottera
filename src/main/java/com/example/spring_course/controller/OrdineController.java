package com.example.spring_course.controller;

import com.example.spring_course.model.OrdineRequest;
import com.example.spring_course.model.OrdineResponse;
import com.example.spring_course.service.OrdineService;
import com.example.spring_course.service.dto.OrdineResponseDTO;
import com.example.spring_course.service.mapper.OrdineMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@Log4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
@Tag(name="UserController", description = "Controller per CRUD sull' ordine")
public class OrdineController {

    private static final Logger logger = LoggerFactory.getLogger(OrdineController.class);
    private final OrdineMapper ordineMapper;
    private final OrdineService service;


    @Operation(summary = "crea un nuovo ordine")
    @ApiResponse(responseCode = "200"
            , description = "Ordine creato correttamente"
            , content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE
            , schema =  @Schema(implementation = OrdineResponse.class)
    ))


    @PostMapping("/ordini")
    public ResponseEntity<OrdineResponse> createUser(@Valid @RequestBody OrdineRequest ordineRequest) {
        OrdineResponseDTO ordineResponseDTO = service.createOrdine(ordineMapper.toRequestDTO(ordineRequest));
        OrdineResponse userResponse = ordineMapper.dtoToResponse(ordineResponseDTO);

        return ResponseEntity.status(userResponse.getHttpStatus()).body(userResponse);
    }

}