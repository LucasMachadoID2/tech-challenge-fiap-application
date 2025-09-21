package com.tech_challenge_fiap.adapters;

import com.tech_challenge_fiap.data.models.OrderDataModel;
import com.tech_challenge_fiap.dtos.OrderResponseDto;
import com.tech_challenge_fiap.entities.order.OrderEntity;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.Objects.nonNull;

@UtilityClass
public class OrderAdapter {

    public static OrderEntity toEntity(OrderDataModel orderDataModel) {
        return OrderEntity.builder()
                .id(orderDataModel.getId())
                .status(orderDataModel.getStatus())
                .clientEntity(nonNull(orderDataModel.getClient()) ? ClientAdapter.toEntity(orderDataModel.getClient()) : null)
                .productEntities(orderDataModel.getProducts().stream().map(ProductAdapter::toEntity).toList())
                .paymentEntity(nonNull(orderDataModel.getPayment()) ? PaymentAdapter.toEntity(orderDataModel.getPayment()) : null)
                .createdAt(orderDataModel.getCreatedAt())
                .build();
    }

    public static OrderDataModel toDataModel(OrderEntity orderEntity) {
        return OrderDataModel.builder()
                .id(orderEntity.getId())
                .status(orderEntity.getStatus())
                .client(nonNull(orderEntity.getClientEntity()) ? ClientAdapter.toDataModel(orderEntity.getClientEntity()) : null)
                .products(orderEntity.getProductEntities().stream().map(ProductAdapter::toDataModel).toList())
                .payment(nonNull(orderEntity.getPaymentEntity()) ? PaymentAdapter.toDataModel(orderEntity.getPaymentEntity()) : null)
                .createdAt(orderEntity.getCreatedAt())
                .build();
    }

    public static OrderDataModel toDataModelWithId(OrderEntity orderEntity) {
        return OrderDataModel.builder()
                .id(UUID.randomUUID().toString()) 
                .status(orderEntity.getStatus())
                .client(nonNull(orderEntity.getClientEntity()) ? ClientAdapter.toDataModel(orderEntity.getClientEntity()) : null)
                .products(orderEntity.getProductEntities().stream().map(ProductAdapter::toDataModel).toList())
                .payment(nonNull(orderEntity.getPaymentEntity()) ? PaymentAdapter.toDataModel(orderEntity.getPaymentEntity()) : null)
                .createdAt(orderEntity.getCreatedAt())
                .build();
    }

    public static OrderResponseDto toResponse(OrderEntity orderEntity) {
        return OrderResponseDto.builder()
                .id(orderEntity.getId())
                .status(orderEntity.getStatus().name()) 
                .client(nonNull(orderEntity.getClientEntity()) ? ClientAdapter.toResponse(orderEntity.getClientEntity()) : null)
                .products(orderEntity.getProductEntities().stream().map(ProductAdapter::toResponse).toList())
                .payment(nonNull(orderEntity.getPaymentEntity()) ? PaymentAdapter.toResponse(orderEntity.getPaymentEntity()) : null)
                .createdAt(orderEntity.getCreatedAt())
                .build();
    }

    public static OrderResponseDto toResponse(OrderDataModel orderDataModel) {
        return OrderResponseDto.builder()
                .id(orderDataModel.getId())
                .status(orderDataModel.getStatus().name())
                .client(nonNull(orderDataModel.getClient()) ? ClientAdapter.toResponse(orderDataModel.getClient()) : null)
                .products(orderDataModel.getProducts().stream().map(ProductAdapter::toResponse).toList())
                .payment(nonNull(orderDataModel.getPayment()) ? PaymentAdapter.toResponse(orderDataModel.getPayment()) : null)
                .createdAt(orderDataModel.getCreatedAt())
                .build();
    }
}