package com.tech_challenge_fiap.adapter.service.outbound.repository;

import com.tech_challenge_fiap.adapter.service.outbound.entity.OrderEntity;
import com.tech_challenge_fiap.core.domain.order.Order;
import com.tech_challenge_fiap.core.domain.order.OrderRepository;
import com.tech_challenge_fiap.util.converter.OrderConverter;
import com.tech_challenge_fiap.util.exception.OrderNotFoundException;
import com.tech_challenge_fiap.util.exception.OrdersNotFoundExpection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.tech_challenge_fiap.util.converter.OrderConverter.entityToOrder;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final MongoOrderRepository mongoOrderRepository;

    @Override
    public Order save(OrderEntity order) {
        var savedOrder = mongoOrderRepository.save(order);
        return entityToOrder(savedOrder);
    }

    @Override
    public Order getOrderById(String id) {
        var order = mongoOrderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return entityToOrder(order);
    }

    @Override
    public List<Order> findAll() {
        List<OrderEntity> orderEntities = mongoOrderRepository.findAll();
        if (orderEntities.isEmpty()) {
            throw new OrdersNotFoundExpection();
        }

        return orderEntities.stream().map(OrderConverter::entityToOrder).collect(Collectors.toList());
    }
}
