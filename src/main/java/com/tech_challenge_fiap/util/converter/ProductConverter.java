package com.tech_challenge_fiap.util.converter;


import com.tech_challenge_fiap.adapter.service.inbound.dto.ProductRequestDto;
import com.tech_challenge_fiap.adapter.service.inbound.dto.ProductResponseDto;
import com.tech_challenge_fiap.core.domain.product.Product;
import com.tech_challenge_fiap.adapter.service.outbound.entity.ProductEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductConverter {

    public static Product toDomain(ProductEntity entity) {
        return Product.builder()
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

    public static ProductEntity toEntity(Product product) {
        return ProductEntity.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .image(product.getImage())
                .price(product.getPrice())
                .priceForClient(product.getPriceForClient())
                .category(product.getCategory())
                .quantity(product.getQuantity())
                .build();
    }

    public static Product toDomain(ProductRequestDto dto) {
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .image(dto.getImage())
                .price(dto.getPrice())
                .priceForClient(dto.getPriceForClient())
                .category(dto.getCategory())
                .quantity(dto.getQuantity())
                .build();
    }

    public static ProductResponseDto toResponse(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .image(product.getImage())
                .price(product.getPrice())
                .priceForClient(product.getPriceForClient())
                .category(product.getCategory())
                .quantity(product.getQuantity())
                .build();
    }
}

