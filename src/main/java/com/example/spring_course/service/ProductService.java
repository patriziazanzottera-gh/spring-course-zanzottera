package com.example.spring_course.service;

import com.example.spring_course.exception.AppException;
import com.example.spring_course.model.OrdineResponse;
import com.example.spring_course.model.ProductRequest;
import com.example.spring_course.model.ProductResponse;
import com.example.spring_course.repository.OrdineRepository;
import com.example.spring_course.repository.ProductMongoRepository;
import com.example.spring_course.repository.model.OrdineEntity;
import com.example.spring_course.repository.model.ProductDocument;
import com.example.spring_course.service.dto.OrdineRequestDTO;
import com.example.spring_course.service.dto.OrdineResponseDTO;
import com.example.spring_course.service.mapper.OrdineMapper;
import com.example.spring_course.service.mapper.ProductMongoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;


@Service
public class ProductService {

private final ProductMongoRepository repository;
private final ProductMongoMapper mapper;


public ProductService(
        ProductMongoRepository repository,
        ProductMongoMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
}


public ProductResponse create(ProductRequest request) {

    ProductDocument document = mapper.toDocument(request);
    ProductDocument saved = repository.save(document);
    return mapper.toResponse(saved);
}

public List<ProductResponse> findAll() {

    return repository.findAll()
            .stream()
            .map(mapper::toResponse)
            .toList();
}

public ProductResponse findById(String id) {

    ProductDocument document = repository.findById(id)
            .orElseThrow(() ->
                    new ResponseStatusException(
                            NOT_FOUND,
                            "Product not found: " +
                                    id
                    )
            );
    return mapper.toResponse(document);
}

public ProductResponse update(
        String id,
        ProductRequest request) {

    ProductDocument document = repository.findById(id)
            .orElseThrow(() ->
                    new ResponseStatusException(
                            NOT_FOUND,
                            "Product not found: " +
                                    id
                    )
            );

    document.setName(request.name());
    document.setDescription(request.description());
    document.setPrice(request.price());
    document.setCategory(request.category());
    ProductDocument updated = repository.save(document);
    return mapper.toResponse(updated);
}


public void delete(String id) {
    if (!repository.existsById(id)) {
        throw new ResponseStatusException(
                NOT_FOUND,
                "Product not found: " + id
        );
    }


    repository.deleteById(id);
}


public List<ProductResponse> findByCategory(String category) {


    return repository.findByCategory(category)
            .stream()
            .map(mapper::toResponse)
            .toList();
}


public List<ProductResponse> searchByName(String name) {


    return repository.findByNameContainingIgnoreCase(name)
            .stream()
            .map(mapper::toResponse)
            .toList();
}
}