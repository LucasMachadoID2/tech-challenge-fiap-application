package com.tech_challenge_fiap.adapter.service.outbound.entity;

import com.tech_challenge_fiap.core.domain.order.OrderStatusEnum;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@EqualsAndHashCode
@Getter
@Setter
@Document(collection = "order")
@Builder
public class OrderEntity {

    @Id
    private String id;

    @NonNull
    private OrderStatusEnum status;

    private ClientEntity client;

    @NonNull
    private List<ProductEntity> products;

    @NonNull
    private PaymentEntity payment;
}
