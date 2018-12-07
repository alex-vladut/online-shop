package com.onlineshop.domain.validation;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = -1175993873831008016L;

	private final String fieldName;
	private final String errorMessage;

	public ValidationException(final String fieldName, final String errorMessage) {
		super(errorMessage + ". Field: " + fieldName);

		this.fieldName = fieldName;
		this.errorMessage = errorMessage;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
