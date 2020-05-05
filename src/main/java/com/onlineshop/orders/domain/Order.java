package com.onlineshop.orders.domain;

import com.datastax.driver.core.DataType.Name;
import com.onlineshop.core.domain.Money;
import com.onlineshop.products.domain.Product;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.unmodifiableList;

@Table("orders")
public class Order {

    @Id
    private final UUID id;
    @Column
    private final EmailAddress buyerEmailAddress;
    @Column
    @Indexed
    @CassandraType(type = Name.TIMESTAMP)
    private final LocalDateTime creationDateTime;
    @Column
    private final List<OrderItem> orderItems;

    private Order(final UUID id, final EmailAddress buyerEmailAddress, final LocalDateTime creationDateTime) {
        this.id = id;
        this.buyerEmailAddress = buyerEmailAddress;
        this.creationDateTime = creationDateTime;
        this.orderItems = new ArrayList<>();
    }

    /**
     * Constructor used to rehydrate the entity from the persistence layer.
     */
    @PersistenceConstructor
    private Order(final UUID id, final EmailAddress buyerEmailAddress, final LocalDateTime creationDateTime, final List<OrderItem> orderItems) {
        this.id = id;
        this.buyerEmailAddress = buyerEmailAddress;
        this.creationDateTime = creationDateTime;
        this.orderItems = orderItems;
    }

    public UUID id() {
        return id;
    }

    public EmailAddress buyerEmailAddress() {
        return buyerEmailAddress;
    }

    public LocalDateTime creationDateTime() {
        return creationDateTime;
    }

    public List<OrderItem> orderItems() {
        return unmodifiableList(orderItems);
    }

    public Money totalPrice() {
        // assuming all prices are expressed in the same currency
        return orderItems().stream()
                .map(item -> item.price().multiply(item.quantity().asBigDecimal()))
                .reduce(Money.ZERO, Money::add);
    }

    public void addOrderItem(final Product product, final Quantity quantity) {
        orderItems.add(OrderItem.newOrderItem(product, quantity));
    }

    public static Order newEmptyOrder(final EmailAddress buyerEmailAddress) {
        return new Order(UUID.randomUUID(), buyerEmailAddress, LocalDateTime.now(ZoneOffset.UTC));
    }

}
