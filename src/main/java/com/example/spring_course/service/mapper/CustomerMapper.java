package com.example.spring_course.service.mapper;

import com.example.spring_course.controller.model.CustomerRequest;
import com.example.spring_course.controller.model.CustomerResponse;
import com.example.spring_course.controller.model.OrderResponse;
import com.example.spring_course.repository.model.CustomerEntity;
import com.example.spring_course.repository.model.OrderEntity;
import com.example.spring_course.service.dto.CustomerRequestDTO;
import com.example.spring_course.service.dto.CustomerResponseDTO;
import com.example.spring_course.service.dto.OrderRequestDTO;
import com.example.spring_course.service.dto.OrderResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerMapper {

    public CustomerResponse toResponse(CustomerRequestDTO customerDTO) {
        List<OrderResponse> orders = customerDTO.getOrders()
                .stream()
                .map(orderDto -> new OrderResponse(
                            orderDto.getId(),
                            orderDto.getTotal()
                        )
                ).toList();
        return new CustomerResponse(customerDTO.getId(), customerDTO.getName(), orders);
    }

    public CustomerRequestDTO toDto(CustomerEntity customerEntity) {
        List<OrderRequestDTO> orders = customerEntity.getOrders()
                .stream()
                .map(orderEntity -> new OrderRequestDTO(
                        orderEntity.getId(),
                        orderEntity.getTotal()
                        )
                ).toList();
        return new CustomerRequestDTO(customerEntity.getId(), customerEntity.getName(),orders);
    }

    public CustomerRequestDTO toRequestDTO(CustomerRequest customerRequest) {
        List<OrderRequestDTO> orders = customerRequest.getOrders()
                .stream()
                .map(orderRequest -> new OrderRequestDTO(
                        orderRequest.getId(),
                        orderRequest.getTotal()
                        )
                ).toList();
        return new CustomerRequestDTO(customerRequest.getId(), customerRequest.getName(),orders);
    }

    public CustomerResponse dtoToResponse(CustomerResponseDTO customerResponseDTO) {
        List<OrderResponse> orders = customerResponseDTO.getOrders()
                .stream()
                .map(orderRequestDTO -> new OrderResponse(
                        orderRequestDTO.getId(),
                        orderRequestDTO.getTotal()
                        )
                ).toList();
        return new CustomerResponse(customerResponseDTO.getId(), customerResponseDTO.getName(),orders);
    }

    public CustomerEntity dtoToEntity(CustomerRequestDTO customerRequestDTO) {
        List<OrderEntity> orders = customerRequestDTO.getOrders()
                .stream()
                .map(orderRequestDTO -> {
                    OrderEntity orderEntity = new OrderEntity();
                    orderEntity.setId(orderRequestDTO.getId());
                    orderEntity.setTotal(orderRequestDTO.getTotal());
                    return orderEntity;
                }).toList();
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(customerRequestDTO.getId());
        customerEntity.setName(customerRequestDTO.getName());
        customerEntity.setOrders(orders);
        return customerEntity;
    }

    public CustomerResponseDTO entityToDTO(CustomerEntity customerEntity) {
        List<OrderResponseDTO> orders = customerEntity.getOrders()
                .stream()
                .map(orderEntity -> {
                    OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
                    orderResponseDTO.setId(orderEntity.getId());
                    orderResponseDTO.setTotal(orderEntity.getTotal());
                    return orderResponseDTO;
                }).toList();
        return new CustomerResponseDTO(customerEntity.getId(), customerEntity.getName(), orders);
    }
}
