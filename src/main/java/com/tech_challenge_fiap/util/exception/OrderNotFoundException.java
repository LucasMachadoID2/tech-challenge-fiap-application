package com.tech_challenge_fiap.util.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String orderId) {
      super(String.format("Could not found order for orderId=%s", orderId));
    }
}
