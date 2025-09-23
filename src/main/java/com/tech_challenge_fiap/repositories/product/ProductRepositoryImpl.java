package com.tech_challenge_fiap.repositories.product;

import com.tech_challenge_fiap.data.models.ProductDataModel;
import com.tech_challenge_fiap.utils.enums.CategoryEnum;
import com.tech_challenge_fiap.utils.exceptions.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private final String tableName = "tech-challenge-products";

    private DynamoDbTable<ProductDataModel> getProductTable() {
        return dynamoDbEnhancedClient.table(tableName, TableSchema.fromBean(ProductDataModel.class));
    }

    @Override
    public ProductDataModel save(ProductDataModel product) {
        try {
            log.info("Saving product with ID: {}", product.getId());
            getProductTable().putItem(product);
            return product;
        } catch (Exception e) {
            log.error("Error saving product with ID: {}", product.getId(), e);
            throw new ProductPersistenceException("Failed to save product: " + product.getName(), e);
        }
    }

    @Override
    public void deleteById(String id) {
        try {
            log.info("Deleting product with ID: {}", id);
            ProductDataModel product = findById(id)
                    .orElseThrow(() -> new ProductNotFoundException(id));
            
            Key key = Key.builder()
                    .partitionValue(AttributeValue.builder().s(id).build())
                    .build();
            getProductTable().deleteItem(key);
        } catch (ProductNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error deleting product with ID: {}", id, e);
            throw new ProductDeletionException("Failed to delete product with ID: " + id, e);
        }
    }

    @Override
    public Optional<ProductDataModel> findById(String id) {
        try {
            log.debug("Finding product by ID: {}", id);
            Key key = Key.builder()
                    .partitionValue(AttributeValue.builder().s(id).build())
                    .build();
            ProductDataModel product = getProductTable().getItem(key);
            return Optional.ofNullable(product);
        } catch (Exception e) {
            log.error("Error finding product by ID: {}", id, e);
            throw new ProductPersistenceException("Failed to find product with ID: " + id, e);
        }
    }

    @Override
    public List<ProductDataModel> findAll() {
        try {
            log.debug("Finding all products");
            List<ProductDataModel> products = getProductTable()
                    .scan()
                    .items()
                    .stream()
                    .collect(Collectors.toList());
            
            if (products.isEmpty()) {
                throw new ProductsNotFoundException();
            }
            return products;
        } catch (ProductsNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error finding all products", e);
            throw new ProductPersistenceException("Failed to retrieve all products", e);
        }
    }

    @Override
    public List<ProductDataModel> findByCategory(CategoryEnum category) {
        try {
            log.debug("Finding products by category: {}", category);
            
            DynamoDbIndex<ProductDataModel> categoryIndex = getProductTable().index("ByCategory");
            Key key = Key.builder()
                    .partitionValue(AttributeValue.builder().s(category.name()).build())
                    .build();
            
            List<ProductDataModel> products = categoryIndex.query(r -> r.queryConditional(QueryConditional.keyEqualTo(key)))
                    .stream()
                    .flatMap(page -> page.items().stream())
                    .collect(Collectors.toList());

            if (products.isEmpty()) {
                throw new ProductsByCategoryNotFoundException(category);
            }
            return products;
        } catch (ProductsByCategoryNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error finding products by category: {}", category, e);
            throw new ProductPersistenceException("Failed to find products by category: " + category, e);
        }
    }

    public List<ProductDataModel> findProductsOnPromotion() {
        try {
            log.debug("Finding products on promotion");
            
            DynamoDbIndex<ProductDataModel> promotionIndex = getProductTable().index("ByPromotion");
            Key key = Key.builder()
                    .partitionValue(AttributeValue.builder().s("true").build())
                    .build();
            
            return promotionIndex.query(r -> r.queryConditional(QueryConditional.keyEqualTo(key)))
                    .stream()
                    .flatMap(page -> page.items().stream())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error finding products on promotion", e);
            throw new ProductPersistenceException("Failed to find products on promotion", e);
        }
    }

    public List<ProductDataModel> findByName(String name) {
        try {
            log.debug("Finding products by name: {}", name);
            
            DynamoDbIndex<ProductDataModel> nameIndex = getProductTable().index("ByName");
            Key key = Key.builder()
                    .partitionValue(AttributeValue.builder().s(name).build())
                    .build();
            
            return nameIndex.query(r -> r.queryConditional(QueryConditional.keyEqualTo(key)))
                    .stream()
                    .flatMap(page -> page.items().stream())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error finding products by name: {}", name, e);
            throw new ProductPersistenceException("Failed to find products by name: " + name, e);
        }
    }

    // Método adicional para buscar produtos por categoria e preço (usando a range key)
    public List<ProductDataModel> findByCategoryAndPriceRange(CategoryEnum category, Double minPrice, Double maxPrice) {
        try {
            log.debug("Finding products by category: {} and price range: {} - {}", category, minPrice, maxPrice);
            
            DynamoDbIndex<ProductDataModel> categoryIndex = getProductTable().index("ByCategory");
            Key key = Key.builder()
                    .partitionValue(AttributeValue.builder().s(category.name()).build())
                    .build();
            
            // Versão com Expression correta
            Expression filterExpression = Expression.builder()
                    .expression("price BETWEEN :minPrice AND :maxPrice")
                    .putExpressionValue(":minPrice", AttributeValue.builder().n(minPrice.toString()).build())
                    .putExpressionValue(":maxPrice", AttributeValue.builder().n(maxPrice.toString()).build())
                    .build();
            
            return categoryIndex.query(r -> r.queryConditional(QueryConditional.keyEqualTo(key))
                    .filterExpression(filterExpression))
                    .stream()
                    .flatMap(page -> page.items().stream())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error finding products by category and price range", e);
            throw new ProductPersistenceException("Failed to find products by category and price range", e);
        }
    }

    // Método alternativo sem filterExpression - filtrar manualmente
    public List<ProductDataModel> findByCategoryAndPriceRangeManual(CategoryEnum category, Double minPrice, Double maxPrice) {
        try {
            log.debug("Finding products by category: {} and price range: {} - {}", category, minPrice, maxPrice);
            
            DynamoDbIndex<ProductDataModel> categoryIndex = getProductTable().index("ByCategory");
            Key key = Key.builder()
                    .partitionValue(AttributeValue.builder().s(category.name()).build())
                    .build();
            
            // Filtrar manualmente após a consulta
            return categoryIndex.query(r -> r.queryConditional(QueryConditional.keyEqualTo(key)))
                    .stream()
                    .flatMap(page -> page.items().stream())
                    .filter(product -> product.getPrice() >= minPrice && product.getPrice() <= maxPrice)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error finding products by category and price range", e);
            throw new ProductPersistenceException("Failed to find products by category and price range", e);
        }
    }
}