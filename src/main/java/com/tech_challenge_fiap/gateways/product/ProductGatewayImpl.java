package com.tech_challenge_fiap.gateways.product;

import com.tech_challenge_fiap.adapters.ProductAdapter;
import com.tech_challenge_fiap.data.models.ProductDataModel;
import com.tech_challenge_fiap.entities.product.ProductEntity;
import com.tech_challenge_fiap.repositories.product.ProductRepository;
import com.tech_challenge_fiap.utils.enums.CategoryEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductGatewayImpl implements ProductGateway {

    private final ProductRepository productRepository;

    @Override
    public ProductEntity save(ProductEntity productEntity) {
        // Verifica se é create ou update
        ProductDataModel productData;

        if (productEntity.getId() == null) {
            // CREATE - gera novo ID
            productData = ProductAdapter.toDataModelWithId(productEntity);
        } else {
            // UPDATE - mantém ID existente
            productData = ProductAdapter.toDataModel(productEntity);
        }

        ProductDataModel savedProduct = productRepository.save(productData);
        return ProductAdapter.toEntity(savedProduct);
    }

    @Override
    public void deleteById(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductEntity findById(String id) {
        Optional<ProductDataModel> product = productRepository.findById(id);
        return product.map(ProductAdapter::toEntity).orElse(null);
    }

    @Override
    public List<ProductEntity> findAll() {
        List<ProductDataModel> products = productRepository.findAll();
        return products.stream().map(ProductAdapter::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<ProductEntity> findByCategory(CategoryEnum category) {
        List<ProductDataModel> products = productRepository.findByCategory(category);
        return products.stream().map(ProductAdapter::toEntity).collect(Collectors.toList());
    }
}
