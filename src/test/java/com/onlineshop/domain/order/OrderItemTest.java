package com.onlineshop.domain.order;

import static com.onlineshop.domain.Money.DEFAULT_CURRENCY;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

import com.onlineshop.domain.Money;
import com.onlineshop.domain.product.Product;

public class OrderItemTest {

	@Test
	public void shouldCreateOrderItem() {
		final Product product = Product.newProduct("Nike Air",
				Money.newMoney(DEFAULT_CURRENCY, new BigDecimal("100.00")));

		final OrderItem orderItem = OrderItem.newOrderItem(product);

		assertNotNull(orderItem);
		assertThat(orderItem.productId(), is(product.id()));
		assertThat(orderItem.price(), is(product.price()));
	}

}
