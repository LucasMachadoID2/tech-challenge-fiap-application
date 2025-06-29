package com.tech_challenge_fiap.util.exception;

import com.tech_challenge_fiap.util.Enum.CategoryEnum;

public class ProductsByCategoryNotFoundException extends RuntimeException {
    public ProductsByCategoryNotFoundException(CategoryEnum category) {
        super(String.format("Could not find products for category=%s", category));
    }
}
