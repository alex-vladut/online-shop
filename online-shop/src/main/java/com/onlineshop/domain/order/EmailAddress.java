package com.onlineshop.domain.order;

import com.onlineshop.domain.validation.Validator;

public class EmailAddress {

	private String value;

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
