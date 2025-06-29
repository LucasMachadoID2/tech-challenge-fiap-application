package com.tech_challenge_fiap.adapter.service.outbound.entity;

import com.tech_challenge_fiap.util.Enum.CategoryEnum;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "product")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    @Id
    private String id;
    private String name;
    private String description;
    private String image;
    private Long price;
    private Long priceForClient;
    private CategoryEnum category;
    private Long quantity;
}
