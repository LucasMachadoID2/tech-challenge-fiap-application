package com.tech_challenge_fiap.repositories.client;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.tech_challenge_fiap.data.models.ClientDataModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClientRepositoryImpl implements ClientRepository {

    private final DynamoDBMapper dynamoDBMapper;

    @Override
    public ClientDataModel save(ClientDataModel client) {
        dynamoDBMapper.save(client);
        return client;
    }

    @Override
    public void deleteById(String id) {
        Optional<ClientDataModel> client = findById(id);
        client.ifPresent(dynamoDBMapper::delete);
    }

    @Override
    public Optional<ClientDataModel> findById(String id) {
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":id", new AttributeValue().withS(id));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("id = :id")
                .withExpressionAttributeValues(expressionAttributeValues);

        List<ClientDataModel> results = dynamoDBMapper.scan(ClientDataModel.class, scanExpression);
        return results.stream().findFirst();
    }

    @Override
    public List<ClientDataModel> findAll() {
        return dynamoDBMapper.scan(ClientDataModel.class, new DynamoDBScanExpression());
    }

    @Override
    public ClientDataModel findByCpf(String cpf) {
        return dynamoDBMapper.load(ClientDataModel.class, cpf);
    }

    @Override
    public void deleteByCpf(String cpf) {
        ClientDataModel client = dynamoDBMapper.load(ClientDataModel.class, cpf);
        if (client != null) {
            dynamoDBMapper.delete(client);
        }
    }
}
