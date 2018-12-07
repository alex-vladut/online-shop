package com.onlineshop.rest;

import static com.onlineshop.domain.Money.newMoney;
import static com.onlineshop.domain.product.Product.newProduct;
import static com.onlineshop.rest.dto.ProductDto.fromDomain;
import static java.util.stream.Collectors.toList;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlineshop.domain.product.Product;
import com.onlineshop.repository.ProductRepository;
import com.onlineshop.rest.dto.ProductDto;

@RestController
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@RequestMapping(path = "/products", method = GET)
	public List<ProductDto> getProducts() {
		return productRepository.findAll().stream().map(ProductDto::fromDomain).collect(toList());
	}

	@RequestMapping(path = "/products/{productId}", method = GET)
	public ResponseEntity<ProductDto> getProduct(@RequestParam final String productId) {
		final Optional<Product> product = productRepository.findById(UUID.fromString(productId));
		if (product.isPresent()) {
			return ResponseEntity.ok(fromDomain(product.get()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@RequestMapping(path = "/products", method = POST)
	public ProductDto createProduct(@RequestBody final ProductDto productDto) {
		final Product createdProduct = productRepository
				.save(newProduct(productDto.name, newMoney(productDto.price.currency, productDto.price.amount)));
		return fromDomain(createdProduct);
	}

	@RequestMapping(path = "/products", method = PUT)
	public ResponseEntity<ProductDto> updateProduct(@RequestBody final ProductDto productDto) {
		final Optional<Product> productOptional = productRepository.findById(UUID.fromString(productDto.id));
		if (productOptional.isPresent()) {
			final Product product = productOptional.get();
			product.update(productDto.name, newMoney(productDto.price.currency, productDto.price.amount));
			return ResponseEntity.ok(fromDomain(product));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
