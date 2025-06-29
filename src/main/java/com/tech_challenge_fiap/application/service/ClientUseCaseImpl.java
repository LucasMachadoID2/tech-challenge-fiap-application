package com.tech_challenge_fiap.application.service;

import com.tech_challenge_fiap.adapter.service.outbound.entity.ClientEntity;
import com.tech_challenge_fiap.adapter.service.outbound.repository.MongoClientRepository;
import com.tech_challenge_fiap.core.domain.client.Client;
import com.tech_challenge_fiap.core.domain.client.ClientUseCase;
import com.tech_challenge_fiap.util.converter.ClientConverter;
import com.tech_challenge_fiap.util.exception.ClientNotFoundException;
import com.tech_challenge_fiap.util.exception.CpfAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.tech_challenge_fiap.util.converter.ClientConverter.toDomain;
import static com.tech_challenge_fiap.util.converter.ClientConverter.toEntity;

@Service
@RequiredArgsConstructor
public class ClientUseCaseImpl implements ClientUseCase {

    private final MongoClientRepository mongoRepository;

    @Override
    public Client create(Client client) {
        boolean cpfExists = mongoRepository.findAll().stream()
                .anyMatch(c -> c.getCpf().equals(client.getCpf()));
        if (cpfExists) {
            throw new CpfAlreadyExistsException(client.getCpf());
        }
        return toDomain(mongoRepository.save(toEntity(client)));
    }

    @Override
    public Client update(String id, Client client) {
        if (mongoRepository.findById(id).isEmpty()) {
            throw new ClientNotFoundException("Cliente não encontrado para o id: " + id);
        }
        ClientEntity entity = toEntity(client);
        entity.setId(id);
        return toDomain(mongoRepository.save(entity));
    }

    @Override
    public void delete(String id) {
        if (mongoRepository.findById(id).isEmpty()) {
            throw new ClientNotFoundException("Cliente não encontrado para o id: " + id);
        }
        mongoRepository.deleteById(id);
    }

    @Override
    public Client findById(String id) {
        return mongoRepository.findById(id)
                .map(ClientConverter::toDomain)
                .orElseThrow(() -> new ClientNotFoundException("Cliente não encontrado para o id: " + id));
    }

    @Override
    public List<Client> findAll() {
        return mongoRepository.findAll().stream()
                .map(ClientConverter::toDomain)
                .toList();
    }
}
