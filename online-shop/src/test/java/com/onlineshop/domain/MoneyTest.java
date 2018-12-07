package com.onlineshop.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.Test;

import com.onlineshop.domain.validation.ValidationException;

public class MoneyTest {

	@Test
	public void shouldCreateMoney_withCurrencyAndAmount() {
		final Currency currency = Currency.getInstance("GBP");
		final BigDecimal amount = new BigDecimal("100.00");

		final Money money = Money.newMoney(currency, amount);

		assertNotNull(money);
		assertThat(money.currency(), is(currency));
		assertThat(money.amount(), is(amount));
	}

	@Test(expected = ValidationException.class)
	public void shouldNotCreateMoney_withCurrencyNotProvided() {
		final Currency currency = null;
		final BigDecimal amount = new BigDecimal("100.00");

		Money.newMoney(currency, amount);
	}

	@Test(expected = ValidationException.class)
	public void shouldNotCreateMoney_withAmountNotProvided() {
		final Currency currency = Currency.getInstance("GBP");
		final BigDecimal amount = null;

		Money.newMoney(currency, amount);
	}

}
