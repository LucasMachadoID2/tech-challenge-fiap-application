package com.tech_challenge_fiap.core.domain.order;

import com.tech_challenge_fiap.adapter.service.outbound.entity.OrderEntity;

import java.util.List;

public interface OrderRepository {

    Order save(OrderEntity order);
    Order getOrderById(String id);
    List<Order> findAll();
}
