package com.onlineshop.domain.order;

import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import com.onlineshop.domain.Money;
import com.onlineshop.domain.product.Product;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@UserDefinedType("orderItem")
public class OrderItem {

	private final UUID productId;
	private final Money price;

	private OrderItem(final UUID productId, final Money price) {
		this.productId = productId;
		this.price = price;
	}

	public UUID productId() {
		return productId;
	}

	public Money price() {
		return price;
	}

	public static OrderItem newOrderItem(final Product product) {
		return new OrderItem(product.id(), product.price());
	}

}
