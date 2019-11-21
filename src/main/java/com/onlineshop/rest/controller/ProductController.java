package com.onlineshop.rest.controller;

import static com.onlineshop.domain.Money.newMoney;
import static com.onlineshop.domain.product.Product.newProduct;
import static com.onlineshop.rest.dto.ProductDto.fromDomain;
import static java.net.URI.create;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.created;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlineshop.domain.product.Product;
import com.onlineshop.repository.ProductRepository;
import com.onlineshop.rest.dto.ProductDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api
@RestController
@RequestMapping("/products")
public class ProductController {

	private ProductRepository productRepository;

	@Autowired
	public ProductController(final ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@ApiOperation("Fetches all the products available")
	@ApiResponses({ @ApiResponse(code = 200, message = "All product successfully retrieved") })
	@GetMapping
	public List<ProductDto> getProducts() {
		return productRepository.findAll().stream().map(ProductDto::fromDomain).collect(toList());
	}

	@ApiOperation("Fetches a single product by its given ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Product with the given ID successfully retrieved"),
			@ApiResponse(code = 404, message = "Product with the given ID does not exist") })
	@GetMapping("/{productId}")
	public ResponseEntity<ProductDto> getProduct(@RequestParam final UUID productId) {
		final Optional<Product> product = productRepository.findById(productId);
		if (product.isPresent()) {
			return ResponseEntity.ok(fromDomain(product.get()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@ApiOperation("Creates a new product")
	@ApiResponses({ @ApiResponse(code = 201, message = "Product successfully created") })
	@PostMapping
	public ResponseEntity<ProductDto> createProduct(@RequestBody final ProductDto productDto) {
		final Product createdProduct = productRepository
				.save(newProduct(productDto.name, newMoney(productDto.price.currency, productDto.price.amount)));
		return created(create("/products/" + createdProduct.id().toString())).body(fromDomain(createdProduct));
	}

	@ApiOperation("Updates the properties of a product")
	@ApiResponses({ @ApiResponse(code = 200, message = "Product with the given ID successfully updated"),
			@ApiResponse(code = 404, message = "Product with the given ID does not exist") })
	@PutMapping("/{productId}")
	public ResponseEntity<ProductDto> updateProduct(@RequestParam final UUID productId,
			@RequestBody final ProductDto productDto) {
		final Optional<Product> productOptional = productRepository.findById(productId);
		if (productOptional.isPresent()) {
			final Product product = productOptional.get();
			product.update(productDto.name, newMoney(productDto.price.currency, productDto.price.amount));
			return ResponseEntity.ok(fromDomain(product));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
