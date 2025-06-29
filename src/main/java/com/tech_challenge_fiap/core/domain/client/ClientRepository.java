package com.tech_challenge_fiap.core.domain.client;



import com.tech_challenge_fiap.adapter.service.outbound.entity.ClientEntity;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    ClientEntity save(ClientEntity client);
    void deleteById(String id);
    Optional<ClientEntity> findById(String id);
    List<ClientEntity> findAll();
}
