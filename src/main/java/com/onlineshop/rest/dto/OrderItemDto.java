package com.onlineshop.rest.dto;

import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.onlineshop.domain.order.OrderItem;
import com.onlineshop.domain.product.Product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Defines the details of an order item")
public class OrderItemDto {

	@NotNull
	@ApiModelProperty(value = "The ID of the product included in the order item", required = true, example = "ea4d2303-8d1c-4f2d-99a4-0f9473d6da9d")
	public UUID productId;

	@NotEmpty
	@ApiModelProperty(value = "The name of the product included in the order item", required = true, example = "Smartphone Nokia 10")
	public String productName;

	@NotNull
	@ApiModelProperty(value = "The price of the product at the time when the order was placed", required = true)
	public MoneyDto price;

	public static OrderItemDto fromDomain(final OrderItem orderItem, final Product product) {
		final OrderItemDto orderItemDto = new OrderItemDto();
		orderItemDto.productId = orderItem.productId();
		orderItemDto.productName = product.name();
		orderItemDto.price = MoneyDto.fromDomain(orderItem.price());
		return orderItemDto;
	}
}
