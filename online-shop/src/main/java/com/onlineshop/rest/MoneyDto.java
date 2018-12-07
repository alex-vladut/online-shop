package com.onlineshop.rest;

import java.math.BigDecimal;
import java.util.Currency;

import com.onlineshop.domain.Money;

public class MoneyDto {

	public Currency currency;
	public BigDecimal amount;

	public static MoneyDto fromDomain(final Money money) {
		final MoneyDto moneyDto = new MoneyDto();
		moneyDto.amount = money.amount();
		moneyDto.currency = money.currency();
		return moneyDto;
	}
}
