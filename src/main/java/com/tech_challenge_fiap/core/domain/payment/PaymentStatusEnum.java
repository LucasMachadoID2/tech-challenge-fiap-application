package com.tech_challenge_fiap.core.domain.payment;

import com.tech_challenge_fiap.util.exception.NotSupportedPaymentStatusException;

public enum PaymentStatusEnum {
    WAITING_PAYMENT,
    PAID,
    CANCELED;

    public static PaymentStatusEnum safeValueOf(String status) {
        try {
            return PaymentStatusEnum.valueOf(status);
        } catch(Exception e) {
            throw new NotSupportedPaymentStatusException(status);
        }
    }
}
