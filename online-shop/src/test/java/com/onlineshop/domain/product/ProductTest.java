package com.onlineshop.domain.product;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.Test;

import com.onlineshop.domain.Money;
import com.onlineshop.domain.validation.ValidationException;

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

	@Test(expected = ValidationException.class)
	public void shouldNotCreateProduct_withNameNotProvided() {
		final String name = null;
		final Money price = Money.newMoney(Currency.getInstance("GBP"), new BigDecimal("1000.00"));

		Product.newProduct(name, price);
	}

	@Test(expected = ValidationException.class)
	public void shouldNotCreateProduct_withPriceNotProvided() {
		final String name = "Armani Suit";
		final Money price = null;

		Product.newProduct(name, price);
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

	@Test(expected = ValidationException.class)
	public void shouldNotUpdateProduct_withNameNotProvided() {
		final Product product = createProduct();
		final String newName = null;
		final Money newPrice = Money.newMoney(Currency.getInstance("GBP"), new BigDecimal("1500.00"));

		product.update(newName, newPrice);
	}

	@Test(expected = ValidationException.class)
	public void shouldNotUpdateProduct_withPriceNotProvided() {
		final Product product = createProduct();
		final String newName = "Gucci Suit";
		final Money newPrice = null;

		product.update(newName, newPrice);
	}

	private Product createProduct() {
		final String name = "Armani Suit";
		final Money price = Money.newMoney(Currency.getInstance("GBP"), new BigDecimal("1000.00"));
		final Product product = Product.newProduct(name, price);
		return product;
	}

}
