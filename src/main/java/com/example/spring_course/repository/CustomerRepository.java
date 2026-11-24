package com.example.spring_course.repository;

import com.example.spring_course.repository.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

}
