package com.tech_challenge_fiap.util.exception;

public class OrdersNotFoundExpection extends RuntimeException {
    public OrdersNotFoundExpection() {
      super("Could not found any order");
    }
}
