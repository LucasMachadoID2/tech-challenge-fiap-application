package com.tech_challenge_fiap.repositories.product;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.tech_challenge_fiap.data.models.ProductDataModel;
import com.tech_challenge_fiap.utils.enums.CategoryEnum;
import com.tech_challenge_fiap.utils.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final DynamoDBMapper dynamoDBMapper;

    @Override
    public ProductDataModel save(ProductDataModel product) {
        try {
            dynamoDBMapper.save(product);
            return product;
        } catch (Exception e) {
            throw new ProductPersistenceException("Failed to save product: " + product.getName(), e);
        }
    }

    @Override
    public void deleteById(String id) {
        try {
            ProductDataModel product = findById(id)
                    .orElseThrow(() -> new ProductNotFoundException(id));
            dynamoDBMapper.delete(product);
        } catch (ProductNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ProductDeletionException("Failed to delete product with ID: " + id, e);
        }
    }

    @Override
    public Optional<ProductDataModel> findById(String id) {
        try {
            ProductDataModel product = dynamoDBMapper.load(ProductDataModel.class, id);
            return Optional.ofNullable(product);
        } catch (Exception e) {
            throw new ProductPersistenceException("Failed to find product with ID: " + id, e);
        }
    }

    @Override
    public List<ProductDataModel> findAll() {
        try {
            List<ProductDataModel> products = dynamoDBMapper.scan(ProductDataModel.class, new DynamoDBScanExpression());
            if (products.isEmpty()) {
                throw new ProductsNotFoundException();
            }
            return products;
        } catch (ProductsNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ProductPersistenceException("Failed to retrieve all products", e);
        }
    }

    @Override
    public List<ProductDataModel> findByCategory(CategoryEnum category) {
        try {
            ProductDataModel product = new ProductDataModel();
            product.setCategory(category);

            DynamoDBQueryExpression<ProductDataModel> queryExpression = new DynamoDBQueryExpression<ProductDataModel>()
                    .withIndexName("ByCategory")
                    .withConsistentRead(false)
                    .withHashKeyValues(product);

            List<ProductDataModel> products = dynamoDBMapper.query(ProductDataModel.class, queryExpression);

            if (products.isEmpty()) {
                throw new ProductsByCategoryNotFoundException(category);
            }
            return products;
        } catch (ProductsByCategoryNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ProductPersistenceException("Failed to find products by category: " + category, e);
        }
    }

    public List<ProductDataModel> findProductsOnPromotion() {
        try {
            ProductDataModel product = new ProductDataModel();
            product.setHasPromotion("true");

            DynamoDBQueryExpression<ProductDataModel> queryExpression = new DynamoDBQueryExpression<ProductDataModel>()
                    .withIndexName("ByPromotion")
                    .withConsistentRead(false)
                    .withHashKeyValues(product);

            return dynamoDBMapper.query(ProductDataModel.class, queryExpression);
        } catch (Exception e) {
            throw new ProductPersistenceException("Failed to find products on promotion", e);
        }
    }

    public List<ProductDataModel> findByName(String name) {
        try {
            ProductDataModel product = new ProductDataModel();
            product.setName(name);

            DynamoDBQueryExpression<ProductDataModel> queryExpression = new DynamoDBQueryExpression<ProductDataModel>()
                    .withIndexName("ByName")
                    .withConsistentRead(false)
                    .withHashKeyValues(product);

            return dynamoDBMapper.query(ProductDataModel.class, queryExpression);
        } catch (Exception e) {
            throw new ProductPersistenceException("Failed to find products by name: " + name, e);
        }
    }
}
