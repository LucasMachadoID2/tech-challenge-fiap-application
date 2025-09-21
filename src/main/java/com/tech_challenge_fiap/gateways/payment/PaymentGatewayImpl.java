package com.tech_challenge_fiap.gateways.payment;

import com.tech_challenge_fiap.adapters.PaymentAdapter;
import com.tech_challenge_fiap.data.models.PaymentDataModel;
import com.tech_challenge_fiap.entities.order.OrderEntity;
import com.tech_challenge_fiap.entities.payment.PaymentEntity;
import com.tech_challenge_fiap.repositories.payment.PaymentRepository;
import com.tech_challenge_fiap.utils.exceptions.CouldNotCreatePaymentException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.amazonaws.services.dynamodbv2.datamodeling.*;

@RequiredArgsConstructor
@Component
public class PaymentGatewayImpl implements PaymentGateway {

    private final DynamoDBMapper dynamoDBMapper;
    private final PaymentRepository paymentRepository;

    @Override
    public PaymentEntity createPayment(OrderEntity orderEntity) {
        try {
            PaymentEntity paymentEntity = paymentRepository.createPayment(orderEntity);

            PaymentDataModel paymentData = PaymentAdapter.toDataModelWithId(paymentEntity);

            dynamoDBMapper.save(paymentData);

            return paymentEntity;

        } catch (Exception e) {
            throw new CouldNotCreatePaymentException(orderEntity.getId(), e);
        }
    }
}
