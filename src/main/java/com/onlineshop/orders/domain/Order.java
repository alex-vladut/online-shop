package com.onlineshop.orders.domain;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.Table;

import com.datastax.driver.core.DataType.Name;
import com.onlineshop.core.domain.Money;
import com.onlineshop.products.domain.Product;
import com.onlineshop.core.ValidationException;

@Table("order_table")
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
		return orderItems().stream().map(OrderItem::price).reduce(Money.ZERO, Money::add);
	}

	public static Order newOrder(final List<Product> products, final EmailAddress buyerEmailAddress) {
		if (products.isEmpty()) {
			throw new ValidationException("products", "At least one product should be provided in order to create an order.");
		}

		final List<OrderItem> orderItems = products.stream().map(OrderItem::newOrderItem).collect(toList());
		return new Order(UUID.randomUUID(), buyerEmailAddress, LocalDateTime.now(ZoneOffset.UTC), orderItems);
	}

}
