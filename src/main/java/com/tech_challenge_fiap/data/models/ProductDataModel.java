package com.tech_challenge_fiap.data.models;

import com.tech_challenge_fiap.utils.enums.CategoryEnum;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;

@DynamoDbBean
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDataModel {

    private String id;
    private CategoryEnum category;
    private String name;
    private String description;
    private String image;
    private Long price;
    private Long priceForClient;
    private Long quantity;
    private String hasPromotion;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDbSortKey
    @DynamoDbSecondaryPartitionKey(indexNames = "ByCategory")
    @DynamoDbAttribute("category")
    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "ByName")
    @DynamoDbAttribute("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDbAttribute("description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @DynamoDbAttribute("image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @DynamoDbSecondarySortKey(indexNames = {"ByCategory", "ByPromotion"})
    @DynamoDbAttribute("price")
    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @DynamoDbAttribute("priceForClient")
    public Long getPriceForClient() {
        return priceForClient;
    }

    public void setPriceForClient(Long priceForClient) {
        this.priceForClient = priceForClient;
    }

    @DynamoDbAttribute("quantity")
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "ByPromotion")
    @DynamoDbAttribute("hasPromotion")
    public String getHasPromotion() {
        return hasPromotion;
    }

    public void setHasPromotion(String hasPromotion) {
        this.hasPromotion = hasPromotion;
    }

    public void setPromotion(boolean isPromotion) {
        this.hasPromotion = isPromotion ? "true" : "false";
    }

    public boolean isPromotion() {
        return "true".equals(this.hasPromotion);
    }
}