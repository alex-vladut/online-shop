package com.onlineshop.rest.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.onlineshop.domain.Money;
import com.onlineshop.domain.product.Product;
import com.onlineshop.repository.ProductRepository;
import com.onlineshop.rest.dto.MoneyDto;
import com.onlineshop.rest.dto.ProductDto;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

	@Mock
	private ProductRepository productRepositoryMock;

	private ProductController productController;

	@Before
	public void setUp() {
		productController = new ProductController(productRepositoryMock);
	}

	@Test
	public void shouldUpdateProduct() {
		final UUID productId = UUID.randomUUID();
		final ProductDto productDto = randomProductDto();
		final Product productMock = mock(Product.class);
		final Money priceMock = mock(Money.class);
		when(productMock.id()).thenReturn(productId);
		when(productMock.price()).thenReturn(priceMock);

		when(productRepositoryMock.findById(productId)).thenReturn(Optional.of(productMock));

		final ResponseEntity<ProductDto> result = productController.updateProduct(productId, productDto);

		assertNotNull(result);
		assertThat(result.getStatusCode(), is(HttpStatus.OK));
		final ArgumentCaptor<String> nameArgCaptor = ArgumentCaptor.forClass(String.class);
		final ArgumentCaptor<Money> priceArgCaptor = ArgumentCaptor.forClass(Money.class);
		verify(productMock).update(nameArgCaptor.capture(), priceArgCaptor.capture());
		assertThat(nameArgCaptor.getValue(), is(productDto.name));
		assertThat(priceArgCaptor.getValue().currency(), is(productDto.price.currency));
		assertThat(priceArgCaptor.getValue().amount(), is(productDto.price.amount));
	}

	@Test
	public void shouldNotUpdateProduct_withProductDoesNotExist() {
		final UUID productId = UUID.randomUUID();
		final ProductDto productDto = randomProductDto();

		when(productRepositoryMock.findById(productId)).thenReturn(Optional.empty());

		final ResponseEntity<ProductDto> result = productController.updateProduct(productId, productDto);

		assertNotNull(result);
		assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
	}

	private ProductDto randomProductDto() {
		final ProductDto productDto = new ProductDto();
		productDto.name = "New name";
		final MoneyDto moneyDto = new MoneyDto();
		moneyDto.amount = new BigDecimal("100.00");
		moneyDto.currency = Currency.getInstance("GBP");
		productDto.price = moneyDto;
		return productDto;
	}

}
