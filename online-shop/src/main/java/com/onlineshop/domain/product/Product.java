package com.onlineshop.domain.product;

import static java.util.UUID.randomUUID;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

import com.onlineshop.domain.Money;

@Table
public class Product {

	@Id
	private final UUID id;
	@Column
	private final String name;
	@Column
	private final Money price;

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

	public static Product newProduct(final String name, final Money price) {
		return new Product(randomUUID(), name, price);
	}

}
