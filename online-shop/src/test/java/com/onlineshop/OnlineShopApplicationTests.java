package com.onlineshop;

import static org.junit.Assert.assertTrue;

import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.onlineshop.domain.product.Product;
import com.onlineshop.repository.ProductRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OnlineShopApplicationTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void contextLoads() {
        final Product product = new Product(UUID.randomUUID(), "Something", 100);
        productRepository.save(product);

        final Optional<Product> savedProduct = productRepository.findById(product.getId());
        assertTrue(savedProduct.isPresent());
    }

}
