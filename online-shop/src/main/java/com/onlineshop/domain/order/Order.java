package com.onlineshop.domain.order;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import com.onlineshop.domain.Money;
import com.onlineshop.domain.product.Product;
import com.onlineshop.domain.validation.ValidationException;
import com.onlineshop.domain.validation.Validator;

public class Order {

	private UUID id;
	private EmailAddress buyerEmailAddress;
	private ZonedDateTime creationDateTime;
	private List<OrderItem> orderItems;

	public Order(final UUID id, final EmailAddress buyerEmailAddress, final ZonedDateTime creationDateTime,
			final List<OrderItem> orderItems) {
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

	public ZonedDateTime creationDateTime() {
		return creationDateTime;
	}

	public List<OrderItem> orderItems() {
		return unmodifiableList(orderItems);
	}

	public Money totalPrice() {
		// assuming all prices are expressed in the same currency
		return orderItems().stream().map(OrderItem::price).reduce(Money.ZERO,
				(firstMoney, secondMoney) -> firstMoney.add(secondMoney));
	}

	public static Order newOrder(final List<Product> products, final EmailAddress buyerEmailAddress) {
		Validator.notNull(products, "products");
		Validator.notNull(buyerEmailAddress, "buyer email address");
		if (products.isEmpty()) {
			throw new ValidationException("products",
					"At least one product should be provided in order to create an order.");
		}

		final List<OrderItem> orderItems = products.stream().map(OrderItem::newOrderItem).collect(toList());
		return new Order(UUID.randomUUID(), buyerEmailAddress, ZonedDateTime.now(ZoneOffset.UTC), orderItems);
	}

}
