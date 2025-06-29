package com.tech_challenge_fiap.core.domain.product;


import com.tech_challenge_fiap.util.Enum.CategoryEnum;
import com.tech_challenge_fiap.util.exception.PriceMustBePositiveException;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(builderClassName = "ProductBuilder", builderMethodName = "internalBuilder")
public class Product {
    private String id;
    private String name;
    private String description;
    private String image;
    private Long price;
    private Long priceForClient;
    private CategoryEnum category;
    private Long quantity;

    public static ProductBuilder builder() {
        return new CustomProductBuilder();
    }

    private static class CustomProductBuilder extends ProductBuilder {
        @Override
        public Product build() {
            validatePrice();
            return super.build();
        }

        private void validatePrice() {
            if (super.price == null || super.price <= 0) {
                throw new PriceMustBePositiveException();
            }
        }
    }
}

