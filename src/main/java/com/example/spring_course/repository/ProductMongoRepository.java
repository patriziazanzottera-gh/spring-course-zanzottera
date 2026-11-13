package com.example.spring_course.repository;

import com.example.spring_course.repository.model.ProductDocument;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductMongoRepository extends MongoRepository<ProductDocument, String> {

    List<ProductDocument> findByCategory(String category);
    List<ProductDocument> findByNameContainingIgnoreCase(String name);

}

