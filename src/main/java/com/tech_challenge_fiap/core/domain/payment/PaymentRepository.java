package com.tech_challenge_fiap.core.domain.payment;

import com.tech_challenge_fiap.core.domain.order.Order;

public interface PaymentRepository {

    Payment createPayment(Order order);
}
