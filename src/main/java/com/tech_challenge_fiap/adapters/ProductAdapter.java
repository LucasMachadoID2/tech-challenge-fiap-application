package com.tech_challenge_fiap.adapters;

import com.tech_challenge_fiap.data.models.ProductDataModel;
import com.tech_challenge_fiap.dtos.ProductRequestDto;
import com.tech_challenge_fiap.dtos.ProductResponseDto;
import com.tech_challenge_fiap.entities.product.ProductEntity;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class ProductAdapter {

    public static ProductEntity toEntity(ProductDataModel entity) {
        return ProductEntity.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .image(entity.getImage())
                .price(entity.getPrice())
                .priceForClient(entity.getPriceForClient())
                .category(entity.getCategory())
                .quantity(entity.getQuantity())
                .build();
    }

    public static ProductDataModel toDataModel(ProductEntity productEntity) {
        return ProductDataModel.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .description(productEntity.getDescription())
                .image(productEntity.getImage())
                .price(productEntity.getPrice())
                .priceForClient(productEntity.getPriceForClient())
                .category(productEntity.getCategory())
                .quantity(productEntity.getQuantity())
                .build();
    }

    public static ProductDataModel toDataModelWithId(ProductEntity productEntity) {
        return ProductDataModel.builder()
                .id(UUID.randomUUID().toString()) 
                .name(productEntity.getName())
                .description(productEntity.getDescription())
                .image(productEntity.getImage())
                .price(productEntity.getPrice())
                .priceForClient(productEntity.getPriceForClient())
                .category(productEntity.getCategory())
                .quantity(productEntity.getQuantity())
                .build();
    }

    public static ProductEntity toEntity(ProductRequestDto dto) {
        return ProductEntity.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .image(dto.getImage())
                .price(dto.getPrice())
                .priceForClient(dto.getPriceForClient())
                .category(dto.getCategory())
                .quantity(dto.getQuantity())
                .build();
    }

    public static ProductResponseDto toResponse(ProductEntity productEntity) {
        return ProductResponseDto.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .description(productEntity.getDescription())
                .image(productEntity.getImage())
                .price(productEntity.getPrice())
                .priceForClient(productEntity.getPriceForClient())
                .category(productEntity.getCategory())
                .quantity(productEntity.getQuantity())
                .build();
    }

    public static ProductResponseDto toResponse(ProductDataModel productDataModel) {
        return ProductResponseDto.builder()
                .id(productDataModel.getId())
                .name(productDataModel.getName())
                .description(productDataModel.getDescription())
                .image(productDataModel.getImage())
                .price(productDataModel.getPrice())
                .priceForClient(productDataModel.getPriceForClient())
                .category(productDataModel.getCategory())
                .quantity(productDataModel.getQuantity())
                .build();
    }
}