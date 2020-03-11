package com.onlineshop.rest.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.onlineshop.core.dto.MoneyDto;
import com.onlineshop.products.ProductController;
import com.onlineshop.products.ProductService;
import com.onlineshop.products.dto.ProductDto;
import com.onlineshop.products.dto.UpdateProductDto;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

	@Mock
	private ProductService productServiceMock;

	private ProductController productController;

	@Before
	public void setUp() {
		productController = new ProductController(productServiceMock);
	}

	@Test
	public void shouldUpdateProduct() {
		final UUID productId = UUID.randomUUID();
		final UpdateProductDto updateProductDto = new UpdateProductDto();
		updateProductDto.name = "Updated name";
		updateProductDto.price = randomMoneyDto();
		ProductDto productDto = new ProductDto();

		when(productServiceMock.update(productId, updateProductDto)).thenReturn(Optional.of(productDto));

		final ResponseEntity<ProductDto> result = productController.updateProduct(productId, updateProductDto);

		assertNotNull(result);
		assertThat(result.getStatusCode(), is(HttpStatus.OK));
	}

	@Test
	public void shouldNotUpdateProduct_withProductDoesNotExist() {
		final UUID productId = UUID.randomUUID();
		final UpdateProductDto productDto = new UpdateProductDto();
		productDto.name = "Updated name";
		productDto.price = randomMoneyDto();

		when(productServiceMock.update(productId, productDto)).thenReturn(Optional.empty());

		final ResponseEntity<ProductDto> result = productController.updateProduct(productId, productDto);

		assertNotNull(result);
		assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
	}

	private MoneyDto randomMoneyDto() {
		final MoneyDto moneyDto = new MoneyDto();
		moneyDto.amount = new BigDecimal("100.00");
		moneyDto.currency = Currency.getInstance("GBP");
		return moneyDto;
	}

}
