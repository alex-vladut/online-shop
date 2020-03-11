package com.onlineshop.orders.domain;

import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import com.onlineshop.core.Validator;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@UserDefinedType("emailAddress")
public class EmailAddress {

	private final String value;

	public EmailAddress(final String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

	public static EmailAddress newEmailAddress(final String emailAddress) {
		Validator.isEmailAddress(emailAddress);
		return new EmailAddress(emailAddress);
	}

}
