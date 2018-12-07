package com.onlineshop.rest;

import static com.onlineshop.domain.Money.newMoney;
import static com.onlineshop.domain.product.Product.newProduct;
import static com.onlineshop.rest.dto.ProductDto.fromDomain;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlineshop.domain.product.Product;
import com.onlineshop.repository.ProductRepository;
import com.onlineshop.rest.dto.ProductDto;

@RestController
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@GetMapping("/products")
	public List<ProductDto> getProducts() {
		return productRepository.findAll().stream().map(ProductDto::fromDomain).collect(toList());
	}

	@GetMapping("/products/{productId}")
	public ResponseEntity<ProductDto> getProduct(@RequestParam final UUID productId) {
		final Optional<Product> product = productRepository.findById(productId);
		if (product.isPresent()) {
			return ResponseEntity.ok(fromDomain(product.get()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/products")
	public ProductDto createProduct(@RequestBody final ProductDto productDto) {
		final Product createdProduct = productRepository
				.save(newProduct(productDto.name, newMoney(productDto.price.currency, productDto.price.amount)));
		return fromDomain(createdProduct);
	}

	@PutMapping("/products")
	public ResponseEntity<ProductDto> updateProduct(@RequestBody final ProductDto productDto) {
		final Optional<Product> productOptional = productRepository.findById(productDto.id);
		if (productOptional.isPresent()) {
			final Product product = productOptional.get();
			product.update(productDto.name, newMoney(productDto.price.currency, productDto.price.amount));
			return ResponseEntity.ok(fromDomain(product));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
