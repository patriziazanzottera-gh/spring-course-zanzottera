package com.example.spring_course.repository;

import com.example.spring_course.repository.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomersRepository extends JpaRepository<CustomerEntity, Long> {


}
