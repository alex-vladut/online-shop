package com.onlineshop.orders.domain;

import com.onlineshop.core.domain.Money;
import com.onlineshop.products.domain.Product;
import lombok.EqualsAndHashCode;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.UUID;

@EqualsAndHashCode
@UserDefinedType("orderItem")
public class OrderItem {

    private final UUID productId;
    private final Money price;
    private final Quantity quantity;

    private OrderItem(final UUID productId, final Money price, final Quantity quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public UUID productId() {
        return productId;
    }

    public Quantity quantity() {
        return quantity;
    }

    public Money price() {
        return price;
    }

    public static OrderItem newOrderItem(final Product product, final Quantity quantity) {
        return new OrderItem(product.id(), product.price(), quantity);
    }

}
