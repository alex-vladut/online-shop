package com.onlineshop.domain.validation;

public final class Validator {

	private Validator() {
		throw new IllegalStateException("Cannot instantiate a Validator.");
	}

	public static void notNull(final Object value, final String fieldName) {
		if (value == null) {
			throw new ValidationException(fieldName, "Should not be null.");
		}
	}

}
