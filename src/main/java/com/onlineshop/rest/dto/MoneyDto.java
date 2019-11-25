package com.onlineshop.rest.dto;

import java.math.BigDecimal;
import java.util.Currency;

import javax.validation.constraints.NotNull;

import com.onlineshop.domain.Money;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Money defined as the combination of a currency and the amount")
public class MoneyDto {

	@NotNull
	@ApiModelProperty(value = "The currency the amount of money is expressed in", required = true, example = "GBP")
	public Currency currency;
	@NotNull
	@ApiModelProperty(value = "The amount of money", required = true, example = "1350.90")
	public BigDecimal amount;

	public static MoneyDto fromDomain(final Money money) {
		final MoneyDto moneyDto = new MoneyDto();
		moneyDto.amount = money.amount();
		moneyDto.currency = money.currency();
		return moneyDto;
	}
}
