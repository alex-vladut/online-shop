package com.onlineshop.domain;

import java.math.BigDecimal;
import java.util.Currency;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import com.datastax.driver.core.DataType.Name;
import com.onlineshop.domain.validation.ValidationException;
import com.onlineshop.domain.validation.Validator;

@UserDefinedType("money")
public class Money {

	public static final Currency DEFAULT_CURRENCY = Currency.getInstance("GBP");
	public static final Money ZERO = new Money(DEFAULT_CURRENCY, BigDecimal.ZERO.setScale(2));

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
		Validator.notNull(currency, "currency");
		Validator.notNull(amount, "amount");

		return new Money(currency, amount);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Money other = (Money) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount)) {
			return false;
		}
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency)) {
			return false;
		}
		return true;
	}

}
