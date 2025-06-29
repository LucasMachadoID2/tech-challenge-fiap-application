package com.tech_challenge_fiap.util.exception;

public class ProductsNotFoundException extends RuntimeException {
    public ProductsNotFoundException() {
        super("Could not find any product.");
    }
}
