package com.onlineshop.domain.order;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.onlineshop.core.ValidationException;
import com.onlineshop.orders.domain.EmailAddress;

public class EmailAddressTest {

	@Test
	public void shouldCreateEmailAddress() {
		final String emailAddressAsString = "test@company.com";

		final EmailAddress emailAddress = EmailAddress.newEmailAddress(emailAddressAsString);

		assertNotNull(emailAddress);
		assertThat(emailAddress.value(), is(emailAddressAsString));
	}

	@Test(expected = ValidationException.class)
	public void shouldNotCreateEmailAddress_withEmailMissingName() {
		final String emailAddressAsString = "company.com";

		EmailAddress.newEmailAddress(emailAddressAsString);
	}

	@Test(expected = ValidationException.class)
	public void shouldNotCreateEmailAddress_withEmailMissingDomain() {
		final String emailAddressAsString = "test@company";

		EmailAddress.newEmailAddress(emailAddressAsString);
	}

}
