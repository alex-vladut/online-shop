package com.onlineshop.domain.validation;

public final class Validator {

	private static final String EMAIL_ADDRESS_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

	private Validator() {
		throw new IllegalStateException("Cannot instantiate a Validator.");
	}

	public static void notNull(final Object value, final String fieldName) {
		if (value == null) {
			throw new ValidationException(fieldName, "Should not be null.");
		}
	}

	public static void isEmailAddress(final String emailAddress) {
		notNull(emailAddress, "email address");

		if (!emailAddress.matches(EMAIL_ADDRESS_REGEX)) {
			throw new ValidationException("email address", "Should be a valid email address.");
		}
	}

}
