package com.onlineshop.repository;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.onlineshop.domain.order.Order;

@Repository
public interface OrderRepository extends CassandraRepository<Order, UUID> {

}
