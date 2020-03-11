package com.onlineshop.domain;

import static com.onlineshop.core.domain.Money.DEFAULT_CURRENCY;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.Test;

import com.onlineshop.core.domain.Money;
import com.onlineshop.core.ValidationException;

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

	@Test
	public void shouldCreateMoney_withCurrencyAndAmountZero() {
		final Currency currency = Currency.getInstance("GBP");
		final BigDecimal amount = new BigDecimal("0.00");

		final Money money = Money.newMoney(currency, amount);

		assertNotNull(money);
		assertThat(money.currency(), is(currency));
		assertThat(money.amount(), is(amount));
	}

	@Test(expected = ValidationException.class)
	public void shouldNotCreateMoney_withAmountLessThanZero() {
		final Currency currency = Currency.getInstance("GBP");
		final BigDecimal amount = new BigDecimal("-10.00");

		Money.newMoney(currency, amount);
	}

	@Test
	public void shouldAddToMoney() {
		final Money money = Money.newMoney(DEFAULT_CURRENCY, new BigDecimal("100.00"));
		final Money anotherMoney = Money.newMoney(DEFAULT_CURRENCY, new BigDecimal("150.00"));

		final Money result = money.add(anotherMoney);

		assertThat(result, is(Money.newMoney(DEFAULT_CURRENCY, new BigDecimal("250.00"))));
	}

	@Test(expected = ValidationException.class)
	public void shouldNotAddToMoney_withDifferentCurrencies() {
		final Money money = Money.newMoney(DEFAULT_CURRENCY, new BigDecimal("100.00"));
		final Money anotherMoney = Money.newMoney(Currency.getInstance("EUR"), new BigDecimal("150.00"));

		money.add(anotherMoney);
	}

}
