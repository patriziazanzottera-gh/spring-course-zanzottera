package com.example.spring_course.service.mapper;

import com.example.spring_course.model.CustomerResponse;
import com.example.spring_course.model.OrderResponse;
import com.example.spring_course.repository.model.CustomerEntity;
import com.example.spring_course.service.dto.CustomerDTO;
import com.example.spring_course.service.dto.OrderDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerMapper {

    public CustomerResponse toResponse(CustomerDTO customerDTO) {
        List<OrderResponse> orders = customerDTO.getOrders()
                .stream()
                .map(orderDto -> new OrderResponse(
                            orderDto.getId(),
                            orderDto.getTotal()
                        )
                ).toList();
        return new CustomerResponse(customerDTO.getId(), customerDTO.getName(), orders);
    }

    public CustomerDTO toDto(CustomerEntity customerEntity) {
        List<OrderDTO> orders = customerEntity.getOrders()
                .stream()
                .map(orderDto -> new OrderDTO(
                        orderDto.getId(),
                        orderDto.getTotal()
                        )
                ).toList();
        return new CustomerDTO(customerEntity.getId(), customerEntity.getName(),orders);
    }
}
