package com.tech_challenge_fiap.adapter.service.outbound.repository;



import com.tech_challenge_fiap.adapter.service.outbound.entity.ClientEntity;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface MongoClientRepository extends MongoRepository<ClientEntity, String> {

}

