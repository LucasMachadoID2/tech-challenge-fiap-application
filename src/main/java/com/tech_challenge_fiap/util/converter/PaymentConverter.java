package com.tech_challenge_fiap.util.converter;

import com.tech_challenge_fiap.adapter.service.inbound.dto.PaymentResponseDto;
import com.tech_challenge_fiap.adapter.service.outbound.entity.PaymentEntity;
import com.tech_challenge_fiap.core.domain.payment.Payment;
import com.tech_challenge_fiap.core.domain.payment.PaymentStatusEnum;
import com.tech_challenge_fiap.util.Enum.PaymentStatusEnumEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PaymentConverter {

    public static Payment toPaymentDomain(com.mercadopago.resources.payment.Payment payment) {
        return com.tech_challenge_fiap.core.domain.payment.Payment.builder()
                .qrImage(payment.getPointOfInteraction().getTransactionData().getQrCodeBase64())
                .qrCode(payment.getPointOfInteraction().getTransactionData().getQrCode())
                .status(PaymentStatusEnum.WAITING_PAYMENT)
                .build();
    }

    public static Payment toPaymentDomain(PaymentEntity paymentEntity) {
        return com.tech_challenge_fiap.core.domain.payment.Payment.builder()
                .id(paymentEntity.getId())
                .qrImage(paymentEntity.getQrImage())
                .qrCode(paymentEntity.getQrCode())
                .status(PaymentStatusEnum.valueOf(paymentEntity.getStatus().name()))
                .build();
    }

    public static PaymentEntity toEntity(Payment payment) {
        return PaymentEntity.builder()
                .id(payment.getId())
                .qrImage(payment.getQrImage())
                .qrCode(payment.getQrCode())
                .status(PaymentStatusEnumEntity.valueOf(payment.getStatus().name()))
                .build();
    }

    public static PaymentResponseDto toResponse(Payment payment) {
        return PaymentResponseDto.builder()
                .id(payment.getId())
                .qrImage(payment.getQrImage())
                .qrCode(payment.getQrCode())
                .status(payment.getStatus().name())
                .build();
    }
}
