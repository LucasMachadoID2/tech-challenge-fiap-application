package com.tech_challenge_fiap.gateways.payment;

import com.tech_challenge_fiap.adapters.PaymentAdapter;
import com.tech_challenge_fiap.data.models.PaymentDataModel;
import com.tech_challenge_fiap.entities.order.OrderEntity;
import com.tech_challenge_fiap.entities.payment.PaymentEntity;
import com.tech_challenge_fiap.repositories.payment.PaymentRepository;
import com.tech_challenge_fiap.utils.exceptions.CouldNotCreatePaymentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Slf4j
@RequiredArgsConstructor
@Component
public class PaymentGatewayImpl implements PaymentGateway {

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private final PaymentRepository paymentRepository;
    private final String tableName = "tech-challenge-payments"; 

    @Override
    public PaymentEntity createPayment(OrderEntity orderEntity) {
        try {
            log.info("Creating payment for order: {}", orderEntity.getId());
            
            PaymentEntity paymentEntity = paymentRepository.createPayment(orderEntity);
            PaymentDataModel paymentData = PaymentAdapter.toDataModelWithId(paymentEntity);
            
            // Obter a referência da tabela e salvar
            DynamoDbTable<PaymentDataModel> paymentTable = dynamoDbEnhancedClient
                .table(tableName, TableSchema.fromBean(PaymentDataModel.class));
            paymentTable.putItem(paymentData);
            
            log.info("Payment created successfully for order: {}", orderEntity.getId());
            return paymentEntity;

        } catch (Exception e) {
            log.error("Failed to create payment for order: {}", orderEntity.getId(), e);
            throw new CouldNotCreatePaymentException(orderEntity.getId(), e);
        }
    }
}