package com.tech_challenge_fiap.util.exception;

public class PriceMustBePositiveException extends RuntimeException {
    public PriceMustBePositiveException() {
        super("Price must not be null or zero or negative.");
    }
}