package com.onlineshop.domain.order;

import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import com.onlineshop.domain.Money;
import com.onlineshop.domain.product.Product;
import com.onlineshop.domain.validation.Validator;

@UserDefinedType("orderItem")
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderItem other = (OrderItem) obj;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price)) {
			return false;
		}
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId)) {
			return false;
		}
		return true;
	}

}
