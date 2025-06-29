package com.tech_challenge_fiap.adapter.service.outbound.repository;

import com.tech_challenge_fiap.adapter.service.outbound.entity.ClientEntity;

import com.tech_challenge_fiap.core.domain.client.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClientRepositoryImpl implements ClientRepository {

    private final MongoClientRepository mongoRepository;

    @Override
    public ClientEntity save(ClientEntity client) {
        return mongoRepository.save(client);
    }

    @Override
    public void deleteById(String id) {
        mongoRepository.deleteById(id);
    }

    @Override
    public Optional<ClientEntity> findById(String id) {
        return mongoRepository.findById(id);
    }

    @Override
    public List<ClientEntity> findAll() {
        return mongoRepository.findAll();
    }
}
