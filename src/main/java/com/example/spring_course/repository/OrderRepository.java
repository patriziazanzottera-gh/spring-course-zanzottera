package com.example.spring_course.repository;

import com.example.spring_course.repository.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {


}

