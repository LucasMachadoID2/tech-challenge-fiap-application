package com.tech_challenge_fiap.repositories.order;

import com.tech_challenge_fiap.data.models.OrderDataModel;
import com.tech_challenge_fiap.entities.order.OrderEntityStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private final String tableName = "tech-challenge-orders";

    private DynamoDbTable<OrderDataModel> getOrderTable() {
        return dynamoDbEnhancedClient.table(tableName, TableSchema.fromBean(OrderDataModel.class));
    }

    @Override
    public OrderDataModel save(OrderDataModel order) {
        try {
            log.info("Saving order with ID: {}", order.getId());
            getOrderTable().putItem(order);
            return order;
        } catch (Exception e) {
            log.error("Error saving order with ID: {}", order.getId(), e);
            throw new RuntimeException("Failed to save order", e);
        }
    }

    @Override
    public Optional<OrderDataModel> findById(String id) {
        try {
            log.debug("Finding order by ID: {}", id);
            
            Expression filterExpression = Expression.builder()
                    .expression("id = :id")
                    .putExpressionValue(":id", AttributeValue.builder().s(id).build())
                    .build();
            
            ScanEnhancedRequest scanRequest = ScanEnhancedRequest.builder()
                    .filterExpression(filterExpression)
                    .build();
            
            List<OrderDataModel> results = getOrderTable()
                    .scan(scanRequest)
                    .items()
                    .stream()
                    .collect(Collectors.toList());
            
            return results.stream().findFirst();
        } catch (Exception e) {
            log.error("Error finding order by ID: {}", id, e);
            throw new RuntimeException("Failed to find order by ID", e);
        }
    }

    @Override
    public List<OrderDataModel> findAllOrderedByStatusAndCreatedAtIgnoringFinalizedAndCreated() {
        try {
            log.debug("Finding all orders ignoring FINALIZED and CREATED status");
            
            Expression filterExpression = Expression.builder()
                    .expression("status <> :status1 AND status <> :status2")
                    .putExpressionValue(":status1", AttributeValue.builder().s(OrderEntityStatusEnum.FINALIZED.name()).build())
                    .putExpressionValue(":status2", AttributeValue.builder().s(OrderEntityStatusEnum.CREATED.name()).build())
                    .build();
            
            ScanEnhancedRequest scanRequest = ScanEnhancedRequest.builder()
                    .filterExpression(filterExpression)
                    .build();
            
            List<OrderDataModel> results = getOrderTable()
                    .scan(scanRequest)
                    .items()
                    .stream()
                    .collect(Collectors.toList());
            
            // Ordenar por status e createdAt
            results.sort(Comparator
                    .comparing(OrderDataModel::getStatus)
                    .thenComparing(OrderDataModel::getCreatedAt));
            
            return results;
        } catch (Exception e) {
            log.error("Error finding all orders", e);
            throw new RuntimeException("Failed to find all orders", e);
        }
    }

    @Override
    public List<OrderDataModel> findByStatus(OrderEntityStatusEnum status) {
        try {
            log.debug("Finding orders by status: {}", status);
            
            // Usando o GSI ByStatus
            DynamoDbIndex<OrderDataModel> statusIndex = getOrderTable().index("ByStatus");
            Key key = Key.builder()
                    .partitionValue(AttributeValue.builder().s(status.name()).build())
                    .build();
            
            return statusIndex.query(r -> r.queryConditional(QueryConditional.keyEqualTo(key)))
                    .stream()
                    .flatMap(page -> page.items().stream())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error finding orders by status: {}", status, e);
            throw new RuntimeException("Failed to find orders by status", e);
        }
    }

    // Método adicional para buscar pedidos por CPF usando o GSI ByCpf
    public List<OrderDataModel> findByCpf(String cpf) {
        try {
            log.debug("Finding orders by CPF: {}", cpf);
            
            DynamoDbIndex<OrderDataModel> cpfIndex = getOrderTable().index("ByCpf");
            Key key = Key.builder()
                    .partitionValue(AttributeValue.builder().s(cpf).build())
                    .build();
            
            return cpfIndex.query(r -> r.queryConditional(QueryConditional.keyEqualTo(key)))
                    .stream()
                    .flatMap(page -> page.items().stream())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error finding orders by CPF: {}", cpf, e);
            throw new RuntimeException("Failed to find orders by CPF", e);
        }
    }

    // Método para buscar pedido pela chave completa (id + createdAt)
    public Optional<OrderDataModel> findByIdAndCreatedAt(String id, String createdAt) {
        try {
            log.debug("Finding order by ID: {} and createdAt: {}", id, createdAt);
            
            Key key = Key.builder()
                    .partitionValue(AttributeValue.builder().s(id).build())
                    .sortValue(AttributeValue.builder().s(createdAt).build())
                    .build();
            
            OrderDataModel order = getOrderTable().getItem(key);
            return Optional.ofNullable(order);
        } catch (Exception e) {
            log.error("Error finding order by ID: {} and createdAt: {}", id, createdAt, e);
            throw new RuntimeException("Failed to find order by ID and createdAt", e);
        }
    }
}