package com.onlineshop.orders;

import static com.onlineshop.orders.domain.EmailAddress.newEmailAddress;
import static com.onlineshop.orders.domain.Order.newOrder;
import static java.util.stream.Collectors.toList;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.onlineshop.orders.domain.EmailAddress;
import com.onlineshop.orders.domain.Order;
import com.onlineshop.orders.domain.OrderItem;
import com.onlineshop.products.domain.Product;
import com.onlineshop.products.ProductRepository;
import com.onlineshop.orders.dto.CreateOrderDto;
import com.onlineshop.orders.dto.OrderDto;

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
