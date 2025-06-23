package com.tech_challenge_fiap.entities.order;

import com.tech_challenge_fiap.core.domain.client.Client;
import com.tech_challenge_fiap.core.domain.payment.Payment;
import com.tech_challenge_fiap.core.domain.product.Product;
import com.tech_challenge_fiap.util.exception.OrderProductsCantBeNullOrEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

import static java.util.Objects.nonNull;

@Getter
@Setter
@Builder
public class OrderEntity {

    private String id;

    @NonNull
    private OrderEntityStatusEnum status;

    private Client client;

    @NonNull
    private List<Product> products;

    private Payment payment;

    public static OrderEntityBuilder builder() {
        return new CustomOrderBuilder();
    }

    public Long getOrderPrice() {
        if(nonNull(this.client)) {
            return this.products.stream().map(product -> {
                if(nonNull(product.getPriceForClient())) {
                    return product.getPriceForClient();
                }
                return product.getPrice();
            }).mapToLong(Long::longValue).sum();
        }

        return this.products.stream().map(Product::getPrice).mapToLong(Long::longValue).sum();
    }

    private static class CustomOrderBuilder extends OrderEntityBuilder {
        @Override
        public OrderEntity build() {
            validateProducts();
            return super.build();
        }

        private void validateProducts() {
            if(super.products.isEmpty()) {
                throw new OrderProductsCantBeNullOrEmpty();
            }
        }
    }
}
