package com.tech_challenge_fiap.application.service.order;

import com.tech_challenge_fiap.adapter.service.inbound.dto.OrderRequestDto;
import com.tech_challenge_fiap.adapter.service.inbound.dto.PaymentRequestDto;
import com.tech_challenge_fiap.core.domain.client.Client;
import com.tech_challenge_fiap.core.domain.client.ClientUseCase;
import com.tech_challenge_fiap.core.domain.order.Order;
import com.tech_challenge_fiap.core.domain.order.OrderRepository;
import com.tech_challenge_fiap.core.domain.order.OrderStatusEnum;
import com.tech_challenge_fiap.core.domain.order.OrderUseCase;
import com.tech_challenge_fiap.core.domain.payment.Payment;
import com.tech_challenge_fiap.core.domain.payment.PaymentRepository;
import com.tech_challenge_fiap.core.domain.payment.PaymentStatusEnum;
import com.tech_challenge_fiap.core.domain.product.Product;
import com.tech_challenge_fiap.core.domain.product.ProductUseCase;
import com.tech_challenge_fiap.util.converter.OrderConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.tech_challenge_fiap.util.converter.OrderConverter.orderToEntity;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class OrderUseCaseImpl implements OrderUseCase {

    private final OrderRepository orderRepository;
    private final ClientUseCase clientUseCase;
    private final ProductUseCase productUseCase;
    private final PaymentRepository paymentRepository;

    @Override
    public Order createOrder(OrderRequestDto orderRequestDto) {
        Client client = null;
        if (nonNull(orderRequestDto.getClientId())) {
            client = clientUseCase.findById(orderRequestDto.getClientId());
        }

        List<Product> products =
                orderRequestDto.getProductIds().stream().map(productUseCase::findById).toList();

        Order order = Order.builder()
                .status(OrderStatusEnum.CREATED)
                .client(client)
                .products(products)
                .build();

        Payment payment = paymentRepository.createPayment(order);

        order.setPayment(payment);

        return orderRepository.save(orderToEntity(order));
    }

    @Override
    public Order updatePaymentStatus(PaymentRequestDto paymentRequestDto) {
        var order = orderRepository.getOrderById(paymentRequestDto.getOrderId());

        PaymentStatusEnum paymentStatusEnum = PaymentStatusEnum.safeValueOf(paymentRequestDto.getStatus());
        order.getPayment().setStatus(paymentStatusEnum);

        return orderRepository.save(orderToEntity(order));
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order updateStatus(String orderId, OrderStatusEnum status) {
        Order orderFound = orderRepository.getOrderById(orderId);

        orderFound.setStatus(status);

        return orderRepository.save(OrderConverter.orderToEntity(orderFound));
    }
}
