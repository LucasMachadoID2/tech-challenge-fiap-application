package com.tech_challenge_fiap.core.domain.product;



import com.tech_challenge_fiap.adapter.service.outbound.entity.ProductEntity;
import com.tech_challenge_fiap.util.Enum.CategoryEnum;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    ProductEntity save(ProductEntity product);
    void deleteById(String id);
    Optional<ProductEntity> findById(String id);
    List<ProductEntity> findAll();
    List<ProductEntity> findByCategory(CategoryEnum category);
}
