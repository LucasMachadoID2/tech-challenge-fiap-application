package com.tech_challenge_fiap.util.exception;

public class NameCannotBeNullOrEmptyException extends RuntimeException {
    public NameCannotBeNullOrEmptyException() {
        super("Name cannot be null or empty.");
    }
}
