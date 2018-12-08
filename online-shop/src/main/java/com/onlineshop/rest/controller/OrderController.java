package com.onlineshop.rest.controller;

import static com.onlineshop.domain.order.EmailAddress.newEmailAddress;
import static com.onlineshop.domain.order.Order.newOrder;
import static com.onlineshop.rest.dto.OrderDto.fromDomain;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlineshop.domain.order.EmailAddress;
import com.onlineshop.domain.order.Order;
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
}
