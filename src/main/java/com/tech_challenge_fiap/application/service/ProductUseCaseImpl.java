package com.tech_challenge_fiap.application.service;

import com.tech_challenge_fiap.adapter.service.outbound.entity.ProductEntity;
import com.tech_challenge_fiap.adapter.service.outbound.repository.MongoProductRepository;
import com.tech_challenge_fiap.core.domain.product.Product;
import com.tech_challenge_fiap.core.domain.product.ProductUseCase;
import com.tech_challenge_fiap.util.Enum.CategoryEnum;
import com.tech_challenge_fiap.util.converter.ProductConverter;
import com.tech_challenge_fiap.util.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.tech_challenge_fiap.util.converter.ProductConverter.toDomain;
import static com.tech_challenge_fiap.util.converter.ProductConverter.toEntity;

@Service
@RequiredArgsConstructor
public class ProductUseCaseImpl implements ProductUseCase {

    private final MongoProductRepository mongoRepository;

    @Override
    public Product create(Product product) {
        return toDomain(mongoRepository.save(toEntity(product)));
    }

    @Override
    public Product update(String id, Product product) {
        ProductEntity entity = toEntity(product);
        entity.setId(id);
        return toDomain(mongoRepository.save(entity));
    }

    @Override
    public void delete(String id) {
        mongoRepository.deleteById(id);
    }

    @Override
    public Product findById(String id) {
        return mongoRepository.findById(id)
                .map(ProductConverter::toDomain)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public List<Product> findAll() {
        return mongoRepository.findAll().stream()
                .map(ProductConverter::toDomain)
                .toList();
    }

    @Override
    public List<Product> findByCategory(CategoryEnum category) {
        return mongoRepository.findByCategory(category)
                .stream()
                .map(ProductConverter::toDomain)
                .toList();
    }
}
