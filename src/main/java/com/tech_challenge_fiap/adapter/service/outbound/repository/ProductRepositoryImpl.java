package com.tech_challenge_fiap.adapter.service.outbound.repository;

import com.tech_challenge_fiap.adapter.service.outbound.entity.ProductEntity;

import com.tech_challenge_fiap.core.domain.product.ProductRepository;
import com.tech_challenge_fiap.util.Enum.CategoryEnum;
import com.tech_challenge_fiap.util.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final MongoProductRepository mongoRepository;

    @Override
    public ProductEntity save(ProductEntity product) {
        try {
            return mongoRepository.save(product);
        } catch (Exception e) {
            throw new ProductPersistenceException("Failed to save product: " + product.getClass().getName(), e);
        }
    }

    @Override
    public void deleteById(String id) {
        if (!mongoRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }

        try {
            mongoRepository.deleteById(id);
        } catch (Exception e) {
            throw new ProductDeletionException("Failed to delete product with ID: " + id, e);
        }
    }

    @Override
    public Optional<ProductEntity> findById(String id) {
        return Optional.ofNullable(
                mongoRepository.findById(id)
                        .orElseThrow(() -> new ProductNotFoundException(id))
        );
    }

    @Override
    public List<ProductEntity> findAll() {
        List<ProductEntity> products = mongoRepository.findAll();
        if (products.isEmpty()) {
            throw new ProductsNotFoundException();
        }
        return products;
    }

    @Override
    public List<ProductEntity> findByCategory(CategoryEnum category) {
        List<ProductEntity> products = mongoRepository.findByCategory(category);
        if (products.isEmpty()) {
            throw new ProductsByCategoryNotFoundException(category);
        }
        return products;
    }
}
