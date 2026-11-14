package com.example.spring_course.controller;

import com.example.spring_course.model.OrdineRequest;
import com.example.spring_course.model.OrdineResponse;
import com.example.spring_course.model.ProductRequest;
import com.example.spring_course.model.ProductResponse;
import com.example.spring_course.service.OrdineService;
import com.example.spring_course.service.ProductService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Log4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/catalog/products")
@Tag(name="ProductController", description = "Controller per CRUD del prodotto")
public class ProductController {

    private final ProductService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@Valid @RequestBody ProductRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<ProductResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ProductResponse findById(@PathVariable String id) {
        return service.findById(id);
    }


    @PutMapping("/{id}")
    public ProductResponse update(
            @PathVariable String id,
            @Valid @RequestBody ProductRequest request) {
        return service.update(id, request);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.delete(id);
    }


    @GetMapping("/category/{category}")
    public List<ProductResponse> findByCategory(@PathVariable String category) {
         return service.findByCategory(category);
    }


    @GetMapping("/search")
    public List<ProductResponse> searchByName(@RequestParam String name) {
    return service.searchByName(name);
    }
}