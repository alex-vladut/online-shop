package com.onlineshop.products;

import static java.net.URI.create;
import static org.springframework.http.ResponseEntity.created;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlineshop.products.dto.CreateProductDto;
import com.onlineshop.products.dto.ProductDto;
import com.onlineshop.products.dto.UpdateProductDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;

@Api
@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

	private final ProductService productService;

	@ApiOperation("Fetches all the products available")
	@ApiResponses({ @ApiResponse(code = 200, message = "All product successfully retrieved") })
	@GetMapping
	public List<ProductDto> getProducts() {
		return productService.getAll();
	}

	@ApiOperation("Fetches a single product by its given ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Product with the given ID successfully retrieved"),
			@ApiResponse(code = 404, message = "Product with the given ID does not exist") })
	@GetMapping("/{productId}")
	public ResponseEntity<ProductDto> getProduct(@PathVariable("productId") final UUID productId) {
		return productService.getById(productId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@ApiOperation("Creates a new product")
	@ApiResponses({ @ApiResponse(code = 201, message = "Product successfully created") })
	@PostMapping
	public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody final CreateProductDto createProductDto) {
		final ProductDto productDto = productService.create(createProductDto);
		return created(create("/products/" + productDto.id.toString())).body(productDto);
	}

	@ApiOperation("Updates the properties of a product")
	@ApiResponses({ @ApiResponse(code = 200, message = "Product with the given ID successfully updated"),
			@ApiResponse(code = 404, message = "Product with the given ID does not exist") })
	@PutMapping("/{productId}")
	public ResponseEntity<ProductDto> updateProduct(@PathVariable("productId") final UUID productId,
			@Valid @RequestBody final UpdateProductDto updateProductDto) {
		return productService.update(productId, updateProductDto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

}
