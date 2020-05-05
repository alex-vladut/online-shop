package com.onlineshop.orders;

import com.onlineshop.core.EntityNotFoundException;
import com.onlineshop.orders.domain.Order;
import com.onlineshop.orders.domain.Quantity;
import com.onlineshop.orders.dto.CreateOrderDto;
import com.onlineshop.orders.dto.CreateOrderItemDto;
import com.onlineshop.orders.dto.OrderDto;
import com.onlineshop.orders.dto.OrderItemDto;
import com.onlineshop.products.ProductRepository;
import com.onlineshop.products.domain.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.onlineshop.orders.domain.EmailAddress.newEmailAddress;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public UUID create(final CreateOrderDto createOrderDto) {
        final Order order = Order.newEmptyOrder(newEmailAddress(createOrderDto.emailAddress));
        for (final CreateOrderItemDto orderItemDto : createOrderDto.products) {
            final Optional<Product> product = productRepository.findById(orderItemDto.productId);
            if (product.isPresent()) {
                order.addOrderItem(product.get(), Quantity.newQuantity(orderItemDto.quantity));
            } else {
                throw new EntityNotFoundException(String.format("Product with id %s not found.", orderItemDto.productId));
            }
        }
        orderRepository.save(order);
        return order.id();
    }

    public List<OrderDto> getAllOrders(final ZonedDateTime startDateTime, final ZonedDateTime endDateTime) {
        final List<Order> orders = orderRepository.findAllOrdersBetween(startDateTime.toLocalDateTime(), endDateTime.toLocalDateTime());
        return orders.stream().map(this::mapOrderToDto).collect(toList());
    }

    private OrderDto mapOrderToDto(final Order order) {
        final var result = OrderDto.fromDomain(order);
        result.orderItems = order.orderItems()
                .stream()
                .map(orderItem -> productRepository.findById(orderItem.productId())
                        .map(product -> OrderItemDto.fromDomain(orderItem, product)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());
        return result;
    }
}
