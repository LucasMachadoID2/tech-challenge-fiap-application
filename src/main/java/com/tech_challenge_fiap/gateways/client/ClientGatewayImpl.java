package com.tech_challenge_fiap.gateways.client;

import com.tech_challenge_fiap.adapters.ClientAdapter;
import com.tech_challenge_fiap.data.models.ClientDataModel;
import com.tech_challenge_fiap.entities.client.ClientEntity;
import com.tech_challenge_fiap.repositories.client.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tech_challenge_fiap.adapters.ClientAdapter.toDataModel;
import static com.tech_challenge_fiap.adapters.ClientAdapter.toEntity;
import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class ClientGatewayImpl implements ClientGateway {

    private final ClientRepository clientRepository;

    @Override
    public ClientEntity save(ClientEntity clientEntity) {
        ClientDataModel clientData = toDataModel(clientEntity);
        ClientDataModel savedClient = clientRepository.save(clientData);
        return toEntity(savedClient);
    }

    @Override
    public void deleteById(String id) {
        clientRepository.deleteById(id);
    }

    @Override
    public void deleteByCpf(String cpf) {
        clientRepository.deleteByCpf(cpf);
    }

   @Override
    public ClientEntity findById(String id) {
        Optional<ClientDataModel> client = clientRepository.findById(id);
        return client.map(ClientAdapter::toEntity).orElse(null);
    }

    @Override
    public List<ClientEntity> findAll() {
        List<ClientDataModel> clients = clientRepository.findAll();
        return clients.stream().map(ClientAdapter::toEntity).collect(Collectors.toList());
    }

    @Override
    public ClientEntity findByCpf(String cpf) {
        ClientDataModel client = clientRepository.findByCpf(cpf);
        return isNull(client) ? null : toEntity(client);
    }
}
