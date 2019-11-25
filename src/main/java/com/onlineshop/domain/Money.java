package com.onlineshop.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import com.datastax.driver.core.DataType.Name;
import com.onlineshop.domain.validation.ValidationException;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@UserDefinedType("money")
public class Money {

	public static final Currency DEFAULT_CURRENCY = Currency.getInstance("GBP");
	public static final Money ZERO = new Money(DEFAULT_CURRENCY, BigDecimal.ZERO.setScale(2, RoundingMode.HALF_DOWN));

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

	public Money add(final Money money) {
		if (!currency().equals(money.currency())) {
			throw new ValidationException("money", "Cannot add money expressed in different currencies");
		}

		return new Money(currency(), amount().add(money.amount()));
	}

	public static Money newMoney(final Currency currency, final BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new ValidationException("amount", "The amount cannot be less than 0.");
		}

		return new Money(currency, amount);
	}

}
