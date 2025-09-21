package com.tech_challenge_fiap.data.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import com.amazonaws.services.dynamodbv2.datamodeling.*;

@DynamoDBTable(tableName = "tech-challenge-users")
@Getter
@Setter
@Builder
public class ClientDataModel {

    @DynamoDBHashKey 
    @DynamoDBAttribute(attributeName = "cpf")
    private String cpf;

    @DynamoDBAttribute
    private String id;

    @DynamoDBAttribute
    private String name;

    @DynamoDBAttribute
    private String email;

    public ClientDataModel() {}

    public ClientDataModel(String id, String name, String cpf, String email) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
    }

}
