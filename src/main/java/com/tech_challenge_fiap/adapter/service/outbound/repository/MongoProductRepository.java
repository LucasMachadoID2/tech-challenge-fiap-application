package com.tech_challenge_fiap.adapter.service.outbound.repository;



import com.tech_challenge_fiap.adapter.service.outbound.entity.ProductEntity;

import com.tech_challenge_fiap.util.Enum.CategoryEnum;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoProductRepository extends MongoRepository<ProductEntity, String> {
    List<ProductEntity> findByCategory(CategoryEnum category);
}

