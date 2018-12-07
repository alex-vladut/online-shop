package com.onlineshop.domain;

import java.math.BigDecimal;
import java.util.Currency;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import com.datastax.driver.core.DataType.Name;
import com.onlineshop.domain.validation.Validator;

@UserDefinedType("money")
public class Money {

	@CassandraType(type = Name.VARCHAR)
	private final Currency currency;
	private final BigDecimal amount;

	private Money(final Currency currency, final BigDecimal amount) {
		this.currency = currency;
		this.amount = amount;
	}

	public Currency currency() {
		return currency;
	}

	public BigDecimal amount() {
		return amount;
	}

	public static Money newMoney(final Currency currency, final BigDecimal amount) {
		Validator.notNull(currency, "currency");
		Validator.notNull(amount, "amount");

		return new Money(currency, amount);
	}

}
