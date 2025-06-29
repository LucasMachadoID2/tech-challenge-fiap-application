package com.tech_challenge_fiap.util.exception;

public class EmailCannotBeNullOrEmptyException extends RuntimeException {
    public EmailCannotBeNullOrEmptyException() {
        super("Email cannot be null or empty");
    }
}
