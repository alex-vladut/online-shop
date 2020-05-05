package com.onlineshop.orders.dto;

import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.onlineshop.orders.domain.OrderItem;
import com.onlineshop.products.domain.Product;
import com.onlineshop.core.dto.MoneyDto;

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
    @ApiModelProperty(value = "The quantity of the product included in the order item", required = true, example = "3")
    public int quantity;

    @NotNull
    @ApiModelProperty(value = "The price of the product at the time when the order was placed", required = true)
    public MoneyDto price;

    public static OrderItemDto fromDomain(final OrderItem orderItem, final Product product) {
        final OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.productId = orderItem.productId();
        orderItemDto.productName = product.name();
        orderItemDto.quantity = orderItem.quantity().value();
        orderItemDto.price = MoneyDto.fromDomain(orderItem.price());
        return orderItemDto;
    }
}
