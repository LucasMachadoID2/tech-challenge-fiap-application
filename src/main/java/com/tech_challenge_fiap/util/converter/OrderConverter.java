package com.tech_challenge_fiap.util.converter;

import com.tech_challenge_fiap.adapter.service.inbound.dto.OrderResponseDto;
import com.tech_challenge_fiap.adapter.service.outbound.entity.OrderEntity;
import com.tech_challenge_fiap.core.domain.order.Order;
import lombok.experimental.UtilityClass;

import static java.util.Objects.nonNull;

@UtilityClass
public class OrderConverter {

    public static Order entityToOrder(OrderEntity orderEntity) {
        return Order.builder()
                .id(orderEntity.getId())
                .status(orderEntity.getStatus())
                .client(nonNull(orderEntity.getClient()) ? ClientConverter.toDomain(orderEntity.getClient()) : null)
                .products(orderEntity.getProducts().stream().map(ProductConverter::toDomain).toList())
                .payment(PaymentConverter.toPaymentDomain(orderEntity.getPayment()))
                .build();
    }

    public static OrderEntity orderToEntity(Order order) {
        return OrderEntity.builder()
                .id(order.getId())
                .status(order.getStatus())
                .client(nonNull(order.getClient()) ? ClientConverter.toEntity(order.getClient()) : null)
                .products(order.getProducts().stream().map(ProductConverter::toEntity).toList())
                .payment(PaymentConverter.toEntity(order.getPayment()))
                .build();
    }

    public static OrderResponseDto toResponse(Order order) {
        return OrderResponseDto.builder()
                .id(order.getId())
                .status(order.getStatus().name())
                .client(nonNull(order.getClient()) ? ClientConverter.toResponse(order.getClient()) : null)
                .products(order.getProducts().stream().map(ProductConverter::toResponse).toList())
                .payment(PaymentConverter.toResponse(order.getPayment()))
                .build();
    }
}
