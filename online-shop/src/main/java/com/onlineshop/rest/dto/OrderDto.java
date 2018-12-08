package com.onlineshop.rest.dto;

import static java.util.stream.Collectors.toList;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import com.onlineshop.domain.order.Order;
import com.onlineshop.domain.order.OrderItem;
import com.onlineshop.domain.product.Product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Defines the details of an order")
public class OrderDto {

	@ApiModelProperty(value = "The ID of the  order", required = true, allowEmptyValue = false, example = "ea4d2303-8d1c-4f2d-99a4-0f9473d6da9d")
	public UUID id;
	@ApiModelProperty(value = "UTC date time when the order was placed", required = true, allowEmptyValue = false, example = "2018-11-19T14:36:25.181")
	public ZonedDateTime creationDateTime;
	@ApiModelProperty(value = "Total price of the order", required = true, allowEmptyValue = false)
	public MoneyDto totalPrice;
	@ApiModelProperty(value = "Order items included in the order", required = true, allowEmptyValue = false)
	public List<OrderItemDto> orderItems;

	public static OrderDto fromDomain(final Order order, final List<Product> products) {
		final OrderDto orderDto = new OrderDto();
		orderDto.id = order.id();
		orderDto.creationDateTime = order.creationDateTime().atZone(ZoneOffset.UTC);
		orderDto.totalPrice = MoneyDto.fromDomain(order.totalPrice());
		orderDto.orderItems = order.orderItems().stream().map(orderItem -> toOrderItemDto(orderItem, products))
				.collect(toList());
		return orderDto;
	}

	private static OrderItemDto toOrderItemDto(final OrderItem orderItem, final List<Product> products) {
		final Product product = products.stream().filter(p -> p.id().equals(orderItem.productId())).findAny()
				.orElseThrow(
						() -> new IllegalArgumentException("No product found for order item " + orderItem.productId()));
		return OrderItemDto.fromDomain(orderItem, product);
	}

}
