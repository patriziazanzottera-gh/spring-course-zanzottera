package com.example.spring_course.batch.processor;

import com.example.spring_course.repository.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ProductItemProcessor implements ItemProcessor<Product, Product> {

    private static final Logger log = LoggerFactory.getLogger(ProductItemProcessor.class);

    @Override
    public Product process(Product product) {
        if (product.getName() == null || product.getName().isBlank()
                || product.getCategory() == null || product.getCategory().isBlank()
                || product.getPrice() == null || product.getPrice().signum() <= 0) {
            log.warn("PRODUCT FILTERED - name={}, price={}", product.getName(), product.getPrice());
            return null;
        }

        product.setName(product.getName().trim());
        product.setCategory(product.getCategory().trim().toUpperCase(Locale.ROOT));

        if (product.getBrand() != null) {
            product.setBrand(product.getBrand().trim());
        }
        if (product.getDescription() != null) {
            product.setDescription(product.getDescription().trim());
        }

        log.debug("PRODUCT PROCESSED - name={}", product.getName());
        return product;
    }
}