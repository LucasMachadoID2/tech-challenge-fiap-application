package com.tech_challenge_fiap.core.domain.order;

public enum OrderStatusEnum {
    CREATED("Criado"),
    RECEIVED("Recebido"),
    IN_PREPARATION("Em preparação");

    public final String description;

    OrderStatusEnum(String description) {
        this.description = description;
    }
}
