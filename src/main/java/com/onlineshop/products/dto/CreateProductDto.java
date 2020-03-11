package com.onlineshop.products.dto;

import javax.validation.constraints.NotNull;

import com.onlineshop.core.dto.MoneyDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Defines the details required to create a new product")
public class CreateProductDto {

	@NotNull
	@ApiModelProperty(value = "The name of a product", required = true, example = "Smartphone Nokia 10")
	public String name;

	@NotNull
	@ApiModelProperty(value = "The price of a product", required = true, example = "100.00 GBP")
	public MoneyDto price;
}
