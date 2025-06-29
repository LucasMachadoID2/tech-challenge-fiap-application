package com.tech_challenge_fiap.core.domain.product;


import com.tech_challenge_fiap.util.Enum.CategoryEnum;

import java.util.List;

public interface ProductUseCase {
    Product create(Product product);
    Product update(String id, Product product);
    void delete(String id);
    Product findById(String id);
    List<Product> findAll();
    List<Product> findByCategory(CategoryEnum category);
}
