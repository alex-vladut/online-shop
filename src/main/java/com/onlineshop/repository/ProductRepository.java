package com.onlineshop.repository;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.onlineshop.domain.product.Product;

@Repository
public interface ProductRepository extends CassandraRepository<Product, UUID> {

}
