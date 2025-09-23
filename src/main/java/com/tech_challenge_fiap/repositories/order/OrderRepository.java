package com.tech_challenge_fiap.repositories.order;

import com.tech_challenge_fiap.data.models.OrderDataModel;
import com.tech_challenge_fiap.entities.order.OrderEntityStatusEnum;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    OrderDataModel save(OrderDataModel order);

    Optional<OrderDataModel> findById(String id);

    List<OrderDataModel> findAllOrderedByStatusAndCreatedAtIgnoringFinalizedAndCreated();

    List<OrderDataModel> findByStatus(OrderEntityStatusEnum status);
}
