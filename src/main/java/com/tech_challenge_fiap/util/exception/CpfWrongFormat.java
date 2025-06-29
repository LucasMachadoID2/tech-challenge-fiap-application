package com.tech_challenge_fiap.util.exception;

public class CpfWrongFormat extends RuntimeException {
    public CpfWrongFormat() {
        super("CPF should have 11 digits");
    }
}
