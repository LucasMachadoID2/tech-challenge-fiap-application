package com.tech_challenge_fiap.repositories.client;

import com.tech_challenge_fiap.data.models.ClientDataModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ClientRepositoryImpl implements ClientRepository {

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private final String tableName = "tech-challenge-users";

    private DynamoDbTable<ClientDataModel> getClientTable() {
        return dynamoDbEnhancedClient.table(tableName, TableSchema.fromBean(ClientDataModel.class));
    }

    @Override
    public ClientDataModel save(ClientDataModel client) {
        try {
            log.info("Saving client with CPF: {}", client.getCpf());
            getClientTable().putItem(client);
            return client;
        } catch (Exception e) {
            log.error("Error saving client with CPF: {}", client.getCpf(), e);
            throw new RuntimeException("Failed to save client", e);
        }
    }

    @Override
    public void deleteById(String cpf) {
        try {
            log.info("Deleting client with CPF: {}", cpf);
            Key key = Key.builder().partitionValue(AttributeValue.builder().s(cpf).build()).build();
            getClientTable().deleteItem(key);
        } catch (Exception e) {
            log.error("Error deleting client with CPF: {}", cpf, e);
            throw new RuntimeException("Failed to delete client", e);
        }
    }

    @Override
    public Optional<ClientDataModel> findById(String cpf) {
        try {
            log.debug("Finding client by CPF: {}", cpf);
            Key key = Key.builder().partitionValue(AttributeValue.builder().s(cpf).build()).build();
            ClientDataModel client = getClientTable().getItem(key);
            return Optional.ofNullable(client);
        } catch (Exception e) {
            log.error("Error finding client by CPF: {}", cpf, e);
            throw new RuntimeException("Failed to find client by CPF", e);
        }
    }

    @Override
    public List<ClientDataModel> findAll() {
        try {
            log.debug("Finding all clients");
            return getClientTable().scan().items().stream().collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error finding all clients", e);
            throw new RuntimeException("Failed to find all clients", e);
        }
    }

    @Override
    public ClientDataModel findByCpf(String cpf) {
        return findById(cpf).orElse(null);
    }

    @Override
    public void deleteByCpf(String cpf) {
        deleteById(cpf);
    }

    public Optional<ClientDataModel> findByEmail(String email) {
        try {
            log.debug("Finding client by email: {}", email);
            
            var emailIndex = getClientTable().index("EmailIndex");
            Key key = Key.builder().partitionValue(AttributeValue.builder().s(email).build()).build();
            
            var result = emailIndex.query(r -> r.queryConditional(QueryConditional.keyEqualTo(key)))
                    .stream()
                    .flatMap(page -> page.items().stream())
                    .findFirst();
            
            return result;
        } catch (Exception e) {
            log.error("Error finding client by email: {}", email, e);
            throw new RuntimeException("Failed to find client by email", e);
        }
    }
}