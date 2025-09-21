package com.tech_challenge_fiap.utils.exceptions;

public class CouldNotCreatePaymentException extends RuntimeException {
    public CouldNotCreatePaymentException(String orderId) {
        super(String.format("Could not create payment for orderId=%s", orderId));
    }

    public CouldNotCreatePaymentException(String orderId, Throwable cause) {
        super(String.format("Could not create payment for orderId=%s", orderId), cause);
    }
}
