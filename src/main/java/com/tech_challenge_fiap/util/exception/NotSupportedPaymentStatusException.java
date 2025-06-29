package com.tech_challenge_fiap.util.exception;

public class NotSupportedPaymentStatusException extends RuntimeException {
    public NotSupportedPaymentStatusException(String paymentStatus) {
        super(String.format("Payment Status '%s' is not supported", paymentStatus));
    }
}
