package com.onlineshop.products;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.onlineshop.products.domain.Product;

@Repository
public interface ProductRepository extends CassandraRepository<Product, UUID> { }
