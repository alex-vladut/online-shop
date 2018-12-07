package com.onlineshop.domain.product;

import static java.util.UUID.randomUUID;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

import com.onlineshop.domain.Money;
import com.onlineshop.domain.validation.Validator;

@Table
public class Product {

	@Id
	private final UUID id;
	@Column
	private String name;
	@Column
	private Money price;

	private Product(final UUID id, final String name, final Money price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public UUID id() {
		return id;
	}

	public String name() {
		return name;
	}

	public Money price() {
		return price;
	}

	public void update(final String name, final Money price) {
		Validator.notNull(name, "product name");
		Validator.notNull(price, "price");

		this.name = name;
		this.price = price;
	}

	public static Product newProduct(final String name, final Money price) {
		Validator.notNull(name, "product name");
		Validator.notNull(price, "price");

		return new Product(randomUUID(), name, price);
	}

}
