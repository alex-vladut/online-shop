package com.onlineshop.domain.order;

import java.util.UUID;

import com.onlineshop.domain.Money;
import com.onlineshop.domain.product.Product;
import com.onlineshop.domain.validation.Validator;

public class OrderItem {

	private UUID productId;
	private Money price;

	public OrderItem(final UUID productId, final Money price) {
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
		Validator.notNull(product, "product");

		return new OrderItem(product.id(), product.price());
	}

}
