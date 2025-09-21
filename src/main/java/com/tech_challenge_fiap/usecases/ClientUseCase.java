package com.tech_challenge_fiap.usecases;

import com.tech_challenge_fiap.entities.client.ClientEntity;
import com.tech_challenge_fiap.gateways.client.ClientGateway;
import com.tech_challenge_fiap.utils.exceptions.ClientNotFoundException;
import com.tech_challenge_fiap.utils.exceptions.CpfAlreadyExistsException;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class ClientUseCase {

    public static ClientEntity create(ClientEntity clientEntity, ClientGateway clientGateway) {
        ClientEntity existingClient = clientGateway.findByCpf(clientEntity.getCpf());

        if (nonNull(existingClient)) {
            throw new CpfAlreadyExistsException(clientEntity.getCpf());
        }

        return clientGateway.save(clientEntity);
    }

    public static ClientEntity update(String id, ClientEntity clientEntity, ClientGateway clientGateway) {
        
        ClientEntity existingClient = clientGateway.findById(id);
        if (isNull(existingClient)) {
            throw new ClientNotFoundException("Cliente não encontrado para o id: " + id);
        }

        if (!existingClient.getCpf().equals(clientEntity.getCpf())) {
            throw new IllegalArgumentException("Não é possível alterar o CPF de um cliente");
        }

        clientEntity.setId(id);
        return clientGateway.save(clientEntity);
    }

    public static void delete(String id, ClientGateway clientGateway) {
        ClientEntity existingClient = clientGateway.findById(id);
        if (isNull(existingClient)) {
            throw new ClientNotFoundException("Cliente não encontrado para o id: " + id);
        }

        clientGateway.deleteByCpf(existingClient.getCpf());
    }

    public static ClientEntity findById(String id, ClientGateway clientGateway) {
        ClientEntity client = clientGateway.findById(id);
        if (isNull(client)) {
            throw new ClientNotFoundException("Cliente não encontrado para o id: " + id);
        }
        return client;
    }

    public static ClientEntity findByCpf(String cpf, ClientGateway clientGateway) {
        ClientEntity client = clientGateway.findByCpf(cpf);
        if (isNull(client)) {
            throw new ClientNotFoundException("Cliente não encontrado para o CPF: " + cpf);
        }
        return client;
    }

    public static List<ClientEntity> findAll(ClientGateway clientGateway) {
        return clientGateway.findAll();
    }
}