package com.onlineshop.rest.controller;

import static com.onlineshop.domain.order.EmailAddress.newEmailAddress;
import static com.onlineshop.domain.order.Order.newOrder;
import static com.onlineshop.rest.dto.OrderDto.fromDomain;
import static java.util.stream.Collectors.toList;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlineshop.domain.order.EmailAddress;
import com.onlineshop.domain.order.Order;
import com.onlineshop.domain.order.OrderItem;
import com.onlineshop.domain.product.Product;
import com.onlineshop.repository.OrderRepository;
import com.onlineshop.repository.ProductRepository;
import com.onlineshop.rest.dto.CreateOrderDto;
import com.onlineshop.rest.dto.OrderDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api
@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ProductRepository productRepository;

	@ApiOperation("Creates a new order")
	@ApiResponses({ @ApiResponse(code = 201, message = "Order successfully created") })
	@PostMapping
	public OrderDto createOrder(@RequestBody final CreateOrderDto createOrderDto) {
		final List<Product> products = createOrderDto.products.stream().map(productRepository::findById)
				.filter(Optional::isPresent).map(Optional::get).collect(toList());
		final EmailAddress buyerEmailAddress = newEmailAddress(createOrderDto.emailAddress);
		final Order order = orderRepository.save(newOrder(products, buyerEmailAddress));
		return fromDomain(order, products);
	}

	@ApiOperation("Fetches all orders between given date-times")
	@ApiResponses({ @ApiResponse(code = 200, message = "Orders successfully retrieved") })
	@GetMapping
	public List<OrderDto> getAllOrdersBetweenDateTimes(
			@RequestParam("startDateTime") @DateTimeFormat(iso = ISO.DATE_TIME) final ZonedDateTime startDateTime,
			@RequestParam("endDateTime") @DateTimeFormat(iso = ISO.DATE_TIME) final ZonedDateTime endDateTime) {
		final List<Order> orders = orderRepository.findAllOrdersBetween(startDateTime.toLocalDateTime(),
				endDateTime.toLocalDateTime());
		return orders.stream().map(this::mapOrderToDto).collect(toList());
	}

	private OrderDto mapOrderToDto(final Order order) {
		final List<Product> products = order.orderItems().stream().map(OrderItem::productId)
				.map(productRepository::findById).filter(Optional::isPresent).map(Optional::get).collect(toList());
		return OrderDto.fromDomain(order, products);
	}

}
