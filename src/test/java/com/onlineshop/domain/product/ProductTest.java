package com.onlineshop.domain.product;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.Test;

import com.onlineshop.core.domain.Money;
import com.onlineshop.products.domain.Product;

public class ProductTest {

	@Test
	public void shouldCreateProduct_withNameAndPrice() {
		final String name = "Armani Suit";
		final Money price = Money.newMoney(Currency.getInstance("GBP"), new BigDecimal("1000.00"));

		final Product product = Product.newProduct(name, price);

		assertNotNull(product);
		assertNotNull(product.id());
		assertThat(product.name(), is(name));
		assertThat(product.price(), is(price));
	}

	@Test
	public void shouldUpdateProduct_withNameAndPrice() {
		final Product product = createProduct();
		final String newName = "Gucci Suit";
		final Money newPrice = Money.newMoney(Currency.getInstance("GBP"), new BigDecimal("1500.00"));

		product.update(newName, newPrice);

		assertNotNull(product);
		assertThat(product.name(), is(newName));
		assertThat(product.price(), is(newPrice));
	}

	private Product createProduct() {
		final String name = "Armani Suit";
		final Money price = Money.newMoney(Currency.getInstance("GBP"), new BigDecimal("1000.00"));
		final Product product = Product.newProduct(name, price);
		return product;
	}

}
