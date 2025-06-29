package com.tech_challenge_fiap.core.domain.order;

import com.tech_challenge_fiap.adapter.service.inbound.dto.OrderRequestDto;
import com.tech_challenge_fiap.adapter.service.inbound.dto.PaymentRequestDto;

import java.util.List;

public interface OrderUseCase {

    Order createOrder(OrderRequestDto orderRequestDTO);
    Order updatePaymentStatus(PaymentRequestDto paymentRequestDto);
    List<Order> findAll();
    Order updateStatus(String orderId, OrderStatusEnum status);
}
