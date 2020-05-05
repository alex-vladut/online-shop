package com.onlineshop.orders.dto;

import static java.util.stream.Collectors.toList;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.onlineshop.orders.domain.Order;
import com.onlineshop.orders.domain.OrderItem;
import com.onlineshop.products.domain.Product;
import com.onlineshop.core.dto.MoneyDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Defines the details of an order")
public class OrderDto {

    @NotNull
    @ApiModelProperty(value = "The ID of the  order", required = true, example = "ea4d2303-8d1c-4f2d-99a4-0f9473d6da9d", accessMode =
            ApiModelProperty.AccessMode.READ_ONLY)
    public UUID id;

    @NotNull
    @ApiModelProperty(value = "UTC date time when the order was placed", required = true, example = "2018-11-19T14:36:25.181")
    public ZonedDateTime creationDateTime;

    @ApiModelProperty(value = "Total price of the order", required = true)
    public MoneyDto totalPrice;

    @NotEmpty
    @ApiModelProperty(value = "Order items included in the order", required = true)
    public List<OrderItemDto> orderItems;

    public static OrderDto fromDomain(final Order order) {
        final OrderDto orderDto = new OrderDto();
        orderDto.id = order.id();
        orderDto.creationDateTime = order.creationDateTime().atZone(ZoneOffset.UTC);
        orderDto.totalPrice = MoneyDto.fromDomain(order.totalPrice());
        return orderDto;
    }

    private static OrderItemDto toOrderItemDto(final OrderItem orderItem, final List<Product> products) {
        final Product product = products.stream()
                .filter(p -> p.id().equals(orderItem.productId()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("No product found for order item " + orderItem.productId()));
        return OrderItemDto.fromDomain(orderItem, product);
    }

}
