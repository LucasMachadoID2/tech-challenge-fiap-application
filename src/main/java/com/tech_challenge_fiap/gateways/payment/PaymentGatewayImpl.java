package com.tech_challenge_fiap.gateways.payment;

import com.tech_challenge_fiap.entities.order.OrderEntity;
import com.tech_challenge_fiap.entities.payment.PaymentEntity;
import com.tech_challenge_fiap.repositories.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.tech_challenge_fiap.infrastructure.configs;

@RequiredArgsConstructor
@Component
public class PaymentGatewayImpl implements PaymentGateway {

    private final DynamoDBMapper dynamoDBMapper;
    private final PaymentRepository paymentRepository;

    @Override
    public PaymentEntity createPayment(OrderEntity orderEntity) {
        try {
            PaymentEntity paymentEntity = mercadoPagoRepository.createPayment(orderEntity);
            
            PaymentDataModel paymentData = PaymentAdapter.toDataModelWithId(paymentEntity);
            
            dynamoDBMapper.save(paymentData);
            
            return paymentEntity;
            
        } catch (Exception e) {
            throw new CouldNotCreatePaymentException(orderEntity.getId(), e);
        }
    }
}
