package com.onlineshop.rest.dto;

import com.onlineshop.domain.product.Product;
import com.onlineshop.rest.MoneyDto;

public class ProductDto {

	public String id;
	public String name;
	public MoneyDto price;

	public static ProductDto fromDomain(Product product) {
		final ProductDto productDto = new ProductDto();
		productDto.id = product.id().toString();
		productDto.name = product.name();
		productDto.price = MoneyDto.fromDomain(product.price());
		return productDto;
	}

}
