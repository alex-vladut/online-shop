package com.onlineshop.orders.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel(description = "Defines the details required for placing a new order")
public class CreateOrderDto {

    @NotNull
    @Email
    @ApiModelProperty(value = "The email address of the buyer", required = true, example = "john.smith@mycompany.org")
    public String emailAddress;

    @NotNull
    @NotEmpty
    @ApiModelProperty(value = "The products to be included in the order", required = true)
    public List<CreateOrderItemDto> products;

}
