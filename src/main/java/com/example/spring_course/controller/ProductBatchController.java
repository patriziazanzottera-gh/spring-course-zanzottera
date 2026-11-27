package com.example.spring_course.controller;

import com.example.spring_course.util.FileHashUtils;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.parameters.InvalidJobParametersException;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.JobRestartException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/batch")
public class ProductBatchController {

    private final JobOperator jobOperator;
    private final JobRepository jobRepository;
    private final Job importProductsJob;

    public ProductBatchController(
            JobOperator jobOperator,
            JobRepository jobRepository,
            Job importProductsJob) {
        this.jobOperator = jobOperator;
        this.jobRepository = jobRepository;
        this.importProductsJob = importProductsJob;
    }

    @PostMapping("/products/import")
    public ResponseEntity<JobStatusResponse> startProductImport() throws JobInstanceAlreadyCompleteException, InvalidJobParametersException, JobExecutionAlreadyRunningException, JobRestartException, IOException, NoSuchAlgorithmException {
        Path csvPath = Path.of("src/main/resources/batch/products.csv");
        String hash = FileHashUtils.sha256(csvPath);

        JobParameters parameters = new JobParametersBuilder()
                .addString("fileHash", hash, true)
                .addString("runId", UUID.randomUUID().toString(), true)
                .toJobParameters();

        JobExecution execution = jobOperator.start(importProductsJob, parameters);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(toResponse(execution));
    }

    @GetMapping("/jobs/{executionId}")
    public ResponseEntity<JobStatusResponse> getJobStatus(@PathVariable long executionId) {
        JobExecution execution = jobRepository.getJobExecution(executionId);
        if (execution == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toResponse(execution));
    }

    private JobStatusResponse toResponse(JobExecution execution) {
        List<StepStatusResponse> steps = execution.getStepExecutions().stream()
                .map(this::toStepResponse)
                .toList();

        return new JobStatusResponse(
                execution.getId(),
                execution.getJobInstance().getJobName(),
                execution.getStatus().name(),
                execution.getExitStatus().getExitCode(),
                execution.getExitStatus().getExitDescription(),
                steps
        );
    }

    private StepStatusResponse toStepResponse(StepExecution step) {
        return new StepStatusResponse(
                step.getStepName(),
                step.getStatus().name(),
                step.getReadCount(),
                step.getWriteCount(),
                step.getFilterCount(),
                step.getReadSkipCount(),
                step.getProcessSkipCount(),
                step.getWriteSkipCount(),
                step.getCommitCount()
        );
    }

    public record JobStatusResponse(
            Long executionId,
            String jobName,
            String status,
            String exitCode,
            String exitDescription,
            List<StepStatusResponse> steps) {
    }

    public record StepStatusResponse(
            String stepName,
            String status,
            long readCount,
            long writeCount,
            long filterCount,
            long readSkipCount,
            long processSkipCount,
            long writeSkipCount,
            long commitCount) {
    }
}