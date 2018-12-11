package com.onlineshop.rest.dto;

import java.util.List;
import java.util.UUID;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Defines the details required for placing a new order")
public class CreateOrderDto {

	@ApiModelProperty(value = "The email address of the buyer", required = true, allowEmptyValue = false, example = "john.smith@mycompany.org")
	public String emailAddress;
	@ApiModelProperty(value = "The IDs of the products to be included in the order", required = true, allowEmptyValue = false, example = "[\"285ea2f5-1a42-4fdf-b500-b8a6554deb9e\", \"933b0774-0fe6-42b7-bdf5-32a26b744dec\"]")
	public List<UUID> products;

}
