package com.tech_challenge_fiap.repositories.client;

import com.tech_challenge_fiap.data.models.ClientDataModel;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    ClientDataModel save(ClientDataModel client);

    void deleteById(String id);

    Optional<ClientDataModel> findById(String id);

    List<ClientDataModel> findAll();

    ClientDataModel findByCpf(String cpf);

    void deleteByCpf(String cpf);
}
