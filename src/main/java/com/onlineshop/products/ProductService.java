package com.onlineshop.products;

import static com.onlineshop.core.domain.Money.newMoney;
import static com.onlineshop.products.domain.Product.newProduct;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.onlineshop.products.domain.Product;
import com.onlineshop.products.dto.CreateProductDto;
import com.onlineshop.products.dto.ProductDto;
import com.onlineshop.products.dto.UpdateProductDto;

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

	public Optional<ProductDto> update(final UUID productId, final UpdateProductDto updateProductDto) {
		return productRepository.findById(productId).map(product -> updateProduct(product, updateProductDto)).map(ProductDto::fromDomain);
	}

	public List<ProductDto> getAll() {
		return productRepository.findAll().stream().map(ProductDto::fromDomain).collect(toList());
	}

	public Optional<ProductDto> getById(final UUID productId) {
		final Optional<Product> product = productRepository.findById(productId);
		return product.map(ProductDto::fromDomain);
	}

	private Product updateProduct(Product product, UpdateProductDto updateProductDto) {
		product.update(updateProductDto.name, newMoney(updateProductDto.price.currency, updateProductDto.price.amount));
		return productRepository.save(product);
	}
}
