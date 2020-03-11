package com.onlineshop.orders;

import static java.net.URI.create;
import static org.springframework.http.ResponseEntity.created;

import java.time.ZonedDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlineshop.orders.dto.CreateOrderDto;
import com.onlineshop.orders.dto.OrderDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;

@Api
@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@ApiOperation("Creates a new order")
	@ApiResponses({ @ApiResponse(code = 201, message = "Order successfully created") })
	@PostMapping
	public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody final CreateOrderDto createOrderDto) {
		final OrderDto orderDto = orderService.create(createOrderDto);
		return created(create("/orders/" + orderDto.id.toString())).body(orderDto);
	}

	@ApiOperation("Fetches all orders between given date-times")
	@ApiResponses({ @ApiResponse(code = 200, message = "Orders successfully retrieved") })
	@GetMapping
	public List<OrderDto> getAllOrders(@RequestParam("startDateTime") @DateTimeFormat(iso = ISO.DATE_TIME) final ZonedDateTime startDateTime,
			@RequestParam("endDateTime") @DateTimeFormat(iso = ISO.DATE_TIME) final ZonedDateTime endDateTime) {
		return orderService.getAllOrders(startDateTime, endDateTime);
	}

}
