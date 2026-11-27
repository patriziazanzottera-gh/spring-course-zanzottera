package com.example.spring_course.batch;

import com.example.spring_course.batch.processor.ProductItemProcessor;
import com.example.spring_course.repository.model.Product;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.ChunkOrientedStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.database.JdbcBatchItemWriter;
import org.springframework.batch.infrastructure.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.FlatFileParseException;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.retry.RetryPolicy;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.time.Duration;

@Configuration
public class ProductImportJobConfig {

    @Bean
    @StepScope
    public FlatFileItemReader<Product> productCsvReader() {
        return new FlatFileItemReaderBuilder<Product>()
                .name("productCsvReader")
                .resource(new ClassPathResource("batch/products.csv"))
                .saveState(true)
                .linesToSkip(1)
                .delimited()
                .names("externalCode", "name", "description", "price", "category", "brand")
                .targetType(Product.class)
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<Product> productJdbcWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Product>()
                .dataSource(dataSource)
                .sql("""
                        INSERT INTO products (name, description, price, category, brand, external_code)
                        VALUES (:name, :description, :price, :category, :brand, :externalCode)
                        ON CONFLICT (external_code)
                        DO UPDATE SET
                            name = EXCLUDED.name,
                            description = EXCLUDED.description,
                            price = EXCLUDED.price,
                            category = EXCLUDED.category,
                            brand = EXCLUDED.brand;
                        """)
                .beanMapped()
                .build();
    }

    @Bean
    public Step importProductsStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            FlatFileItemReader<Product> productCsvReader,
            ProductItemProcessor productItemProcessor,
            JdbcBatchItemWriter<Product> productJdbcWriter) {

        RetryPolicy retryPolicy = RetryPolicy.builder()
                .maxRetries(3)
                .includes(TransientDataAccessException.class)
                .delay(Duration.ofMillis(500))
                .multiplier(2)
                .maxDelay(Duration.ofSeconds(5))
                .build();

        return new StepBuilder("importProductsStep", jobRepository)
                .<Product, Product>chunk(3)
                .transactionManager(transactionManager)
                .reader(productCsvReader)
                .processor(productItemProcessor)
                .writer(productJdbcWriter)
                .retryPolicy(retryPolicy)
                .faultTolerant()
                .skip(FlatFileParseException.class)
                .skipLimit(5)
                .build();
    }

    @Bean
    public Job importProductsJob(JobRepository jobRepository, Step importProductsStep) {
        return new JobBuilder("importProductsJob", jobRepository)
                .start(importProductsStep)
                .build();
    }
}