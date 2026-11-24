package com.example.spring_course.controller;

import com.example.spring_course.controller.model.OrderRequest;
import com.example.spring_course.controller.model.OrderResponse;
import com.example.spring_course.service.OrderService;
import com.example.spring_course.service.dto.OrderResponseDTO;
import com.example.spring_course.service.mapper.OrderMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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

//@Log4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
@Tag(name="OrderController", description = "Controller per CRUD sull' ordine")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderMapper orderMapper;
    private final OrderService service;

    @Operation(summary = "crea un nuovo ordine")
    @ApiResponse(responseCode = "200"
            , description = "Ordine creato correttamente"
            , content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE
            , schema =  @Schema(implementation = OrderResponse.class)
    ))

    @PostMapping("/ordini")
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        OrderResponseDTO orderResponseDTO = service.createOrder(orderMapper.toRequestDTO(orderRequest));
        OrderResponse orderResponse = orderMapper.dtoToResponse(orderResponseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
    }

    @GetMapping("/ordini/{id}")
    public OrderResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/ordini/{id}")
    public OrderResponse update(@PathVariable Long id, @Valid @RequestBody OrderRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/ordini/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}