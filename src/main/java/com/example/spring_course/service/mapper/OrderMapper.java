package com.example.spring_course.service.mapper;

import com.example.spring_course.controller.model.CustomerResponse;
import com.example.spring_course.controller.model.OrderRequest;
import com.example.spring_course.controller.model.OrderResponse;
import com.example.spring_course.repository.model.CustomerEntity;
import com.example.spring_course.repository.model.OrderEntity;
import com.example.spring_course.service.dto.CustomerRequestDTO;
import com.example.spring_course.service.dto.CustomerResponseDTO;
import com.example.spring_course.service.dto.OrderRequestDTO;
import com.example.spring_course.service.dto.OrderResponseDTO;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class OrderMapper {


    public OrderResponse dtoToResponse(OrderResponseDTO ordineResponseDTO) {
        CustomerResponse customerResponse = new CustomerResponse();
        if (ordineResponseDTO.getCustomer() != null) {
            customerResponse.setId(ordineResponseDTO.getCustomer().getId());
            customerResponse.setName(ordineResponseDTO.getCustomer().getName());
            customerResponse.setEmail(ordineResponseDTO.getCustomer().getEmail());
        }
        return new OrderResponse(ordineResponseDTO.getId(), ordineResponseDTO.getTotal(), customerResponse);
    }

    public OrderRequestDTO toRequestDTO(@Valid OrderRequest ordineRequest) {
        CustomerRequestDTO customerRequestDTO = new CustomerRequestDTO();
        if (ordineRequest.getCustomer() != null) {
            customerRequestDTO.setId(ordineRequest.getCustomer().getId());
            customerRequestDTO.setName(ordineRequest.getCustomer().getName());
            customerRequestDTO.setEmail(ordineRequest.getCustomer().getEmail());
        }
        return new OrderRequestDTO(ordineRequest.getId(), ordineRequest.getTotal(), customerRequestDTO);
    }

    public OrderEntity requestToEntity(OrderRequest request) {
        CustomerEntity customerEntity = new CustomerEntity();
        if (request.getCustomer() != null) {
            customerEntity.setName(request.getCustomer().getName());
            customerEntity.setEmail(request.getCustomer().getEmail());
            customerEntity.setId(request.getCustomer().getId());
        }
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(request.getId());
        orderEntity.setTotal(request.getTotal());
        orderEntity.setCustomer(customerEntity);
        return  orderEntity;
    }

    public OrderResponse toResponse(OrderEntity ordineEntity) {
        CustomerResponse customerResponse = new CustomerResponse();
        if (ordineEntity.getCustomer() != null) {
            customerResponse.setId(ordineEntity.getCustomer().getId());
            customerResponse.setName(ordineEntity.getCustomer().getName());
            customerResponse.setEmail(ordineEntity.getCustomer().getEmail());
        }
        return new OrderResponse(ordineEntity.getId(), ordineEntity.getTotal(), customerResponse);
    }

    public OrderResponseDTO entityToDTO(OrderEntity orderEntity){
        CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();
        if (orderEntity.getCustomer() != null) {
            customerResponseDTO.setId(orderEntity.getCustomer().getId());
            customerResponseDTO.setName(orderEntity.getCustomer().getName());
            customerResponseDTO.setEmail(orderEntity.getCustomer().getEmail());
        }
        return new OrderResponseDTO(orderEntity.getId(), orderEntity.getTotal(), customerResponseDTO);
    }

    public OrderEntity dtoToEntity(OrderRequestDTO orderRequestDTO) {
        CustomerEntity customerEntity = new CustomerEntity();
        if (orderRequestDTO.getCustomer() != null) {
            customerEntity.setId(orderRequestDTO.getCustomer().getId());
            customerEntity.setName(orderRequestDTO.getCustomer().getName());
            customerEntity.setEmail(orderRequestDTO.getCustomer().getEmail());
        }
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(orderRequestDTO.getId());
        orderEntity.setTotal(orderRequestDTO.getTotal());
        orderEntity.setCustomer(customerEntity);
        return orderEntity;
    }
}