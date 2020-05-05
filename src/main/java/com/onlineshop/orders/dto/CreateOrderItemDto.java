package com.onlineshop.orders.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

@ApiModel(description = "Defines the details required creating an order item")
public class CreateOrderItemDto {

    @NotNull
    @ApiModelProperty(value = "A product to be included in the order", required = true, example = "933b0774-0fe6-42b7-bdf5-32a26b744dec")
    public UUID productId;

    @Positive
    @Max(240)
    @ApiModelProperty(value = "The number of items to be included in the order", required = true, example = "3")
    public int quantity;
}
