package com.onlineshop.service;

import static com.onlineshop.domain.Money.newMoney;
import static com.onlineshop.domain.product.Product.newProduct;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.onlineshop.domain.product.Product;
import com.onlineshop.repository.ProductRepository;
import com.onlineshop.rest.dto.CreateProductDto;
import com.onlineshop.rest.dto.ProductDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	public ProductDto create(final CreateProductDto productDto) {
		final Product createdProduct = productRepository.save(
				newProduct(productDto.name, newMoney(productDto.price.currency, productDto.price.amount)));
		return ProductDto.fromDomain(createdProduct);
	}

	public Optional<ProductDto> update(final UUID productId, final ProductDto productDto) {
		return productRepository.findById(productId).map(product -> updateProduct(product, productDto)).map(ProductDto::fromDomain);
	}

	public List<ProductDto> getAll() {
		return productRepository.findAll().stream().map(ProductDto::fromDomain).collect(toList());
	}

	public Optional<ProductDto> getById(final UUID productId) {
		final Optional<Product> product = productRepository.findById(productId);
		return product.map(ProductDto::fromDomain);
	}

	private Product updateProduct(Product product, @RequestBody @Valid ProductDto productDto) {
		product.update(productDto.name, newMoney(productDto.price.currency, productDto.price.amount));
		return productRepository.save(product);
	}
}
