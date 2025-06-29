package com.tech_challenge_fiap.adapter.service.outbound.repository;

import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.tech_challenge_fiap.core.domain.order.Order;
import com.tech_challenge_fiap.core.domain.payment.PaymentRepository;
import com.tech_challenge_fiap.util.exception.CouldNotCreatePaymentException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static com.tech_challenge_fiap.util.converter.PaymentConverter.toPaymentDomain;
import static java.util.Objects.nonNull;

@Component
public class MercadoPagoPaymentRepository implements PaymentRepository {

    @Value("${pix.expiration.time.minutes}")
    private String pixExpirationTimeMinutes;

    private final static String IDENTIFICATION_CPF = "CPF";
    private final static String TIMEZONE_BR = "-03:00";
    private final static String PAYMENT_METHOD_PIX = "pix";
    private final static String CPF_USER_TEST = "85713983056";
    private final static String MAIL_USER_TEST = "mail@mail.com";
    private final static String NAME_USER_TEST = "User Test";

    @Override
    public com.tech_challenge_fiap.core.domain.payment.Payment createPayment(Order order) {

        try {
            PaymentClient paymentClient = new PaymentClient();
            PaymentPayerRequest paymentPayerRequest = createPaymentPayerRequest(order);

            PaymentCreateRequest paymentCreateRequest = PaymentCreateRequest.builder()
                    .transactionAmount(BigDecimal.valueOf(order.getOrderPrice()))
                    .payer(paymentPayerRequest)
                    .dateOfExpiration(OffsetDateTime.now().plusMinutes(Long.parseLong(pixExpirationTimeMinutes))
                            .withOffsetSameInstant(ZoneOffset.of(TIMEZONE_BR)))
                    .paymentMethodId(PAYMENT_METHOD_PIX)
                    .build();

            Payment payment = paymentClient.create(paymentCreateRequest);

            return toPaymentDomain(payment);
        } catch (MPException | MPApiException ex) {
            throw new CouldNotCreatePaymentException(order.getId());
        }
    }

    private PaymentPayerRequest createPaymentPayerRequest(Order order) {
        if (nonNull(order.getClient())) {
            return PaymentPayerRequest.builder()
                    .email(order.getClient().getEmail())
                    .firstName(order.getClient().getName())
                    .identification(
                            IdentificationRequest.builder()
                                    .type(IDENTIFICATION_CPF)
                                    .number(order.getClient().getCpf())
                                    .build()
                    )
                    .build();
        }

        return PaymentPayerRequest.builder()
                .email(MAIL_USER_TEST)
                .firstName(NAME_USER_TEST)
                .identification(
                        IdentificationRequest.builder()
                                .type(IDENTIFICATION_CPF)
                                .number(CPF_USER_TEST)
                                .build()
                )
                .build();
    }
}
