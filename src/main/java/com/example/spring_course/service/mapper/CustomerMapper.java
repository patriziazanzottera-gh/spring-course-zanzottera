package com.example.spring_course.service.mapper;

import com.example.spring_course.controller.model.CustomerRequest;
import com.example.spring_course.controller.model.CustomerResponse;
import com.example.spring_course.controller.model.OrderResponse;
import com.example.spring_course.repository.CustomerRepository;
import com.example.spring_course.repository.model.CustomerEntity;
import com.example.spring_course.repository.model.OrderEntity;
import com.example.spring_course.service.dto.CustomerRequestDTO;
import com.example.spring_course.service.dto.CustomerResponseDTO;
import com.example.spring_course.service.dto.OrderRequestDTO;
import com.example.spring_course.service.dto.OrderResponseDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerMapper {


    public CustomerResponse toResponse(CustomerRequestDTO customerDTO) {
        List<OrderResponse> orders = new ArrayList<>();
        if (customerDTO.getOrders() != null && !customerDTO.getOrders().isEmpty()) {
                orders = customerDTO.getOrders()
                    .stream()
                    .map(orderDto -> new OrderResponse(
                                    orderDto.getId(),
                                    orderDto.getTotal()
                            )
                    ).toList();
        }
        return new CustomerResponse(customerDTO.getId(), customerDTO.getName(), customerDTO.getEmail(), orders);
    }

    public CustomerRequestDTO toDto(CustomerEntity customerEntity) {
        List<OrderRequestDTO> orders = new ArrayList<>();
        if (customerEntity.getOrders()!= null && !customerEntity.getOrders().isEmpty()) {
            orders = customerEntity.getOrders().stream()
                    .map(orderRequest -> new OrderRequestDTO(
                                orderRequest.getId(),
                                orderRequest.getTotal()
                            )
                    )
                    .toList();
        }
        return new CustomerRequestDTO(customerEntity.getId(), customerEntity.getName(), customerEntity.getEmail(), orders);
    }

    public CustomerRequestDTO toRequestDTO(CustomerRequest customerRequest) {
        List<OrderRequestDTO> orders = new ArrayList<>();
        if (customerRequest.getOrders() != null && !customerRequest.getOrders().isEmpty()) {
            orders = customerRequest.getOrders()
                    .stream()
                    .map(orderRequest -> new OrderRequestDTO(
                                    orderRequest.getId(),
                                    orderRequest.getTotal()
                            )
                    ).toList();
        }
        return new CustomerRequestDTO(customerRequest.getId(), customerRequest.getName(), customerRequest.getEmail(), orders);
    }

    public CustomerResponse dtoToResponse(CustomerResponseDTO customerResponseDTO){
        List<OrderResponse> orders = new ArrayList<>();
        if (customerResponseDTO.getOrders() != null && !customerResponseDTO.getOrders().isEmpty()) {
            orders = customerResponseDTO.getOrders()
                    .stream()
                    .map(orderRequestDTO -> new OrderResponse(
                                    orderRequestDTO.getId(),
                                    orderRequestDTO.getTotal()
                            )
                    ).toList();
        }
        return new CustomerResponse(customerResponseDTO.getId(), customerResponseDTO.getName(), customerResponseDTO.getEmail(), orders);
    }

    public CustomerResponseDTO entityToDTO(CustomerEntity customerEntity) {
        List<OrderResponseDTO> orders = new ArrayList<>();
        if (customerEntity.getOrders() != null && !customerEntity.getOrders().isEmpty()) {
            orders = customerEntity.getOrders()
                    .stream()
                    .map(orderEntity -> {
                        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
                        orderResponseDTO.setId(orderEntity.getId());
                        orderResponseDTO.setTotal(orderEntity.getTotal());
                        return orderResponseDTO;
                    }).toList();
        }
        return new CustomerResponseDTO(customerEntity.getId(), customerEntity.getName(), customerEntity.getEmail(),orders);
    }
}
