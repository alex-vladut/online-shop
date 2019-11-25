package com.onlineshop.rest.dto;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.onlineshop.domain.product.Product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;

@ApiModel(description = "Defines the structure of a Product")
public class ProductDto {

	@NotNull
	@ApiModelProperty(value = "The ID of a product, generated when a new product is created", accessMode = AccessMode.READ_ONLY)
	public UUID id;

	@NotNull
	@ApiModelProperty(value = "The name of a product", required = true, example = "Smartphone Nokia 10")
	public String name;

	@NotNull
	@ApiModelProperty(value = "The price of a product", required = true, example = "100.00 GBP")
	public MoneyDto price;

	public static ProductDto fromDomain(Product product) {
		final ProductDto productDto = new ProductDto();
		productDto.id = product.id();
		productDto.name = product.name();
		productDto.price = MoneyDto.fromDomain(product.price());
		return productDto;
	}

}
