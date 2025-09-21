package com.tech_challenge_fiap.gateways.client;

import com.tech_challenge_fiap.entities.client.ClientEntity;

import java.util.List;

public interface ClientGateway {
    ClientEntity save(ClientEntity client);

    void deleteById(String id);

    ClientEntity findById(String id);

    List<ClientEntity> findAll();

    ClientEntity findByCpf(String cpf);

    void deleteByCpf(String cpf);
}
