package com.tech_challenge_fiap.repositories.order;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.tech_challenge_fiap.data.models.OrderDataModel;
import com.tech_challenge_fiap.entities.order.OrderEntityStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import com.tech_challenge_fiap.infrastructure.configs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final DynamoDBMapper dynamoDBMapper;

    @Override
    public OrderDataModel save(OrderDataModel order) {
        dynamoDBMapper.save(order);
        return order;
    }

    @Override
    public Optional<OrderDataModel> findById(String id) {
        OrderDataModel order = dynamoDBMapper.load(OrderDataModel.class, id);
        return Optional.ofNullable(order);
    }

    @Override
    public List<OrderDataModel> findAllOrderedByStatusAndCreatedAtIgnoringFinalizedAndCreated() {
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":status1", new AttributeValue().withS(OrderEntityStatusEnum.FINALIZED.name()));
        expressionAttributeValues.put(":status2", new AttributeValue().withS(OrderEntityStatusEnum.CREATED.name()));
        
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
            .withFilterExpression("status <> :status1 AND status <> :status2")
            .withExpressionAttributeValues(expressionAttributeValues);
        
        List<OrderDataModel> results = dynamoDBMapper.scan(OrderDataModel.class, scanExpression);
        
        results.sort((o1, o2) -> {
            int statusCompare = o1.getStatus().compareTo(o2.getStatus());
            if (statusCompare != 0) return statusCompare;
            return o1.getCreatedAt().compareTo(o2.getCreatedAt());
        });
        
        return results;
    }

    public List<OrderDataModel> findByStatus(OrderEntityStatusEnum status) {
        OrderDataModel order = new OrderDataModel();
        order.setStatus(status);
        
        DynamoDBQueryExpression<OrderDataModel> queryExpression = new DynamoDBQueryExpression<OrderDataModel>()
            .withIndexName("ByStatus") 
            .withConsistentRead(false)
            .withHashKeyValues(order);
        
        return dynamoDBMapper.query(OrderDataModel.class, queryExpression);
    }
}
