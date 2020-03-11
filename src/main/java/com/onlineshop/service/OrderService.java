package com.onlineshop.service;

import static com.onlineshop.domain.order.EmailAddress.newEmailAddress;
import static com.onlineshop.domain.order.Order.newOrder;
import static java.util.stream.Collectors.toList;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.onlineshop.domain.order.EmailAddress;
import com.onlineshop.domain.order.Order;
import com.onlineshop.domain.order.OrderItem;
import com.onlineshop.domain.product.Product;
import com.onlineshop.repository.OrderRepository;
import com.onlineshop.repository.ProductRepository;
import com.onlineshop.rest.dto.CreateOrderDto;
import com.onlineshop.rest.dto.OrderDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;

	public OrderDto create(final CreateOrderDto createOrderDto) {
		final List<Product> products = createOrderDto.products.stream()
				.map(productRepository::findById)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(toList());
		final EmailAddress buyerEmailAddress = newEmailAddress(createOrderDto.emailAddress);
		final Order order = orderRepository.save(newOrder(products, buyerEmailAddress));
		return OrderDto.fromDomain(order, products);
	}

	public List<OrderDto> getAllOrders(final ZonedDateTime startDateTime, final ZonedDateTime endDateTime) {
		final List<Order> orders = orderRepository.findAllOrdersBetween(startDateTime.toLocalDateTime(), endDateTime.toLocalDateTime());
		return orders.stream().map(this::mapOrderToDto).collect(toList());
	}

	private OrderDto mapOrderToDto(final Order order) {
		final List<Product> products = order.orderItems()
				.stream()
				.map(OrderItem::productId)
				.map(productRepository::findById)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(toList());
		return OrderDto.fromDomain(order, products);
	}
}
