package com.tech_challenge_fiap.adapter.service.outbound.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "client")
@Getter
@Setter
@Builder
public class ClientEntity {
    @Id
    private String id;
    private String name;
    private String cpf;
    private String email;
}
