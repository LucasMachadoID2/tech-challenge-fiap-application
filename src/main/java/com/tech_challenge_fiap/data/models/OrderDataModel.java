package com.tech_challenge_fiap.data.models;

import com.tech_challenge_fiap.entities.order.OrderEntityStatusEnum;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;

import java.time.LocalDateTime;
import java.util.List;

@DynamoDbBean
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDataModel {

    private String id;
    private OrderEntityStatusEnum status;
    private ClientDataModel client;
    private List<ProductDataModel> products;
    private PaymentDataModel payment;
    private LocalDateTime createdAt;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDbAttribute("status")
    public OrderEntityStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderEntityStatusEnum status) {
        this.status = status;
    }

    @DynamoDbAttribute("client")
    public ClientDataModel getClient() {
        return client;
    }

    public void setClient(ClientDataModel client) {
        this.client = client;
    }

    @DynamoDbAttribute("products")
    public List<ProductDataModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDataModel> products) {
        this.products = products;
    }

    @DynamoDbAttribute("payment")
    public PaymentDataModel getPayment() {
        return payment;
    }

    public void setPayment(PaymentDataModel payment) {
        this.payment = payment;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("createdAt")
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}