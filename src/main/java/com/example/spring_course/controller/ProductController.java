package com.example.spring_course.controller;

import com.example.spring_course.controller.model.ProductRequest;
import com.example.spring_course.controller.model.ProductResponse;
import com.example.spring_course.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Log4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/catalog/products")
@Tag(name="ProductController", description = "Controller per CRUD del prodotto")
public class ProductController {

    private final ProductService service;

    @PostMapping("/admin/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@Valid @RequestBody ProductRequest request) {
        return service.create(request);
    }

    @GetMapping("/public/all")
    public List<ProductResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/user/{id}")
    public ProductResponse findById(@PathVariable String id) {
        return service.findById(id);
    }

    @PutMapping("/admin/update/{id}")
    public ProductResponse update(@PathVariable String id, @Valid @RequestBody ProductRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/admin/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

    @GetMapping("/user/category/{category}")
    public List<ProductResponse> findByCategory(@PathVariable String category) {
        return service.findByCategory(category);
    }

    @GetMapping("/user/search")
    public List<ProductResponse> searchByName(@RequestParam String name) {
        return service.searchByName(name);
    }
}