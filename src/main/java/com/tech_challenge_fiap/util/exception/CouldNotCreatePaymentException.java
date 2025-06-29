package com.tech_challenge_fiap.util.exception;

public class CouldNotCreatePaymentException extends RuntimeException {
    public CouldNotCreatePaymentException(String orderId) {
        super(String.format("Could not create payment for orderId=%s", orderId));
    }
}
