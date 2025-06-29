package com.tech_challenge_fiap.core.domain.client;


import java.util.List;

public interface ClientUseCase {
    Client create(Client client);
    Client update(String id, Client client);
    void delete(String id);
    Client findById(String id);
    List<Client> findAll();
}
