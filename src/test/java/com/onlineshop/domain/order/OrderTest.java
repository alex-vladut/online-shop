package com.onlineshop.domain.order;

import static com.onlineshop.core.domain.Money.DEFAULT_CURRENCY;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import com.onlineshop.core.domain.Money;
import com.onlineshop.products.domain.Product;
import com.onlineshop.core.ValidationException;
import com.onlineshop.orders.domain.EmailAddress;
import com.onlineshop.orders.domain.Order;
import com.onlineshop.orders.domain.OrderItem;

public class OrderTest {

	@Test
	public void shouldCreateOrder_withProducts() {
		final Product product = Product.newProduct("Nike Air",
				Money.newMoney(DEFAULT_CURRENCY, new BigDecimal("100.00")));
		final EmailAddress buyerEmailAddress = EmailAddress.newEmailAddress("customer@comp.org");

		final Order order = Order.newOrder(Collections.singletonList(product), buyerEmailAddress);

		assertNotNull(order);
		assertNotNull(order.id());
		assertNotNull(order.creationDateTime());
		assertThat(order.buyerEmailAddress(), is(buyerEmailAddress));
		assertThat(order.orderItems().size(), is(1));
		final OrderItem orderItem = order.orderItems().get(0);
		assertThat(orderItem.productId(), is(product.id()));
		assertThat(orderItem.price(), is(product.price()));
	}

	@Test(expected = ValidationException.class)
	public void shouldNotCreateOrder_withNoProductProvided() {
		final EmailAddress buyerEmailAddress = EmailAddress.newEmailAddress("customer@comp.org");

		Order.newOrder(Collections.emptyList(), buyerEmailAddress);
	}

	@Test
	public void shouldCalculateTotalOrderPrice() {
		final Product product1 = Product.newProduct("Nike Air",
				Money.newMoney(DEFAULT_CURRENCY, new BigDecimal("120.00")));
		final Product product2 = Product.newProduct("Adidas t-shirt",
				Money.newMoney(DEFAULT_CURRENCY, new BigDecimal("250.99")));
		final Product product3 = Product.newProduct("Puma perfume",
				Money.newMoney(DEFAULT_CURRENCY, new BigDecimal("500.10")));
		final EmailAddress buyerEmailAddress = EmailAddress.newEmailAddress("customer@comp.org");
		final Order order = Order.newOrder(Arrays.asList(product1, product2, product3), buyerEmailAddress);
		final Money expectedTotalPrice = Money.newMoney(DEFAULT_CURRENCY, new BigDecimal("871.09"));

		final Money totalPrice = order.totalPrice();

		assertThat(totalPrice, is(expectedTotalPrice));
	}

}
