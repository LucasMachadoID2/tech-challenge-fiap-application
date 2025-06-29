package com.tech_challenge_fiap.util.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String productId) {
        super(String.format("Could not found product for productId=%s", productId));
    }
}
