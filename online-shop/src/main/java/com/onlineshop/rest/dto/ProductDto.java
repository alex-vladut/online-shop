package com.onlineshop.rest.dto;

import java.util.UUID;

import com.onlineshop.domain.product.Product;

public class ProductDto {

	public UUID id;
	public String name;
	public MoneyDto price;

	public static ProductDto fromDomain(Product product) {
		final ProductDto productDto = new ProductDto();
		productDto.id = product.id();
		productDto.name = product.name();
		productDto.price = MoneyDto.fromDomain(product.price());
		return productDto;
	}

}
