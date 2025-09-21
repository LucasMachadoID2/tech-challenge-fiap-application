package com.tech_challenge_fiap.adapters;

import com.tech_challenge_fiap.data.models.PaymentDataModel;
import com.tech_challenge_fiap.dtos.PaymentResponseDto;
import com.tech_challenge_fiap.entities.payment.PaymentEntity;
import com.tech_challenge_fiap.entities.payment.PaymentStatusEnum;
import com.tech_challenge_fiap.utils.enums.PaymentStatusEnumEntity;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class PaymentAdapter {

    public static PaymentEntity toEntity(com.mercadopago.resources.payment.Payment payment) {
        return PaymentEntity.builder()
                .qrImage(payment.getPointOfInteraction().getTransactionData().getQrCodeBase64())
                .qrCode(payment.getPointOfInteraction().getTransactionData().getQrCode())
                .status(PaymentStatusEnum.CREATED) 
                .build();
    }

    public static PaymentEntity toEntity(PaymentDataModel paymentDataModel) {
        return PaymentEntity.builder()
                .id(paymentDataModel.getId())
                .qrImage(paymentDataModel.getQrImage())
                .qrCode(paymentDataModel.getQrCode())
                .status(convertStatusToDomain(paymentDataModel.getStatus())) 
                .build();
    }

    public static PaymentDataModel toDataModel(PaymentEntity paymentEntity) {
        return PaymentDataModel.builder()
                .id(paymentEntity.getId())
                .qrImage(paymentEntity.getQrImage())
                .qrCode(paymentEntity.getQrCode())
                .status(convertStatusToEntity(paymentEntity.getStatus())) 
                .build();
    }

    public static PaymentDataModel toDataModelWithId(PaymentEntity paymentEntity) {
        return PaymentDataModel.builder()
                .id(UUID.randomUUID().toString())
                .qrImage(paymentEntity.getQrImage())
                .qrCode(paymentEntity.getQrCode())
                .status(convertStatusToEntity(paymentEntity.getStatus()))
                .build();
    }

    public static PaymentResponseDto toResponse(PaymentEntity paymentEntity) {
        return PaymentResponseDto.builder()
                .id(paymentEntity.getId())
                .qrImage(paymentEntity.getQrImage())
                .qrCode(paymentEntity.getQrCode())
                .status(paymentEntity.getStatus().name()) 
                .build();
    }

    public static PaymentResponseDto toResponse(PaymentDataModel paymentDataModel) {
        return PaymentResponseDto.builder()
                .id(paymentDataModel.getId())
                .qrImage(paymentDataModel.getQrImage())
                .qrCode(paymentDataModel.getQrCode())
                .status(paymentDataModel.getStatus().name()) 
                .build();
    }

    private static PaymentStatusEnum convertStatusToDomain(PaymentStatusEnumEntity statusEntity) {
        if (statusEntity == null) return null;
        
        switch (statusEntity) {
            case CREATED: return PaymentStatusEnum.CREATED;
            case PAID: return PaymentStatusEnum.APPROVED;    
            case REFUSED: return PaymentStatusEnum.REJECTED; 
            default: return PaymentStatusEnum.CREATED;
        }
    }

    private static PaymentStatusEnumEntity convertStatusToEntity(PaymentStatusEnum status) {
        if (status == null) return null;
        
        switch (status) {
            case CREATED: return PaymentStatusEnumEntity.CREATED;
            case APPROVED: return PaymentStatusEnumEntity.PAID;    
            case REJECTED: return PaymentStatusEnumEntity.REFUSED; 
            case CANCELLED: return PaymentStatusEnumEntity.REFUSED;
            default: return PaymentStatusEnumEntity.CREATED;
        }
    }
}