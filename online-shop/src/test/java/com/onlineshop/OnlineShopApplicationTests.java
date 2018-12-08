package com.onlineshop;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.onlineshop.domain.Money;
import com.onlineshop.domain.order.EmailAddress;
import com.onlineshop.domain.order.Order;
import com.onlineshop.domain.product.Product;
import com.onlineshop.repository.OrderRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OnlineShopApplicationTests {

	@Autowired
	private OrderRepository orderRepository;

	@Test
	public void contextLoads() {
		final Product product = Product.newProduct("Something",
				Money.newMoney(Currency.getInstance("EUR"), BigDecimal.valueOf(100)));
		Order order = Order.newOrder(Arrays.asList(product), EmailAddress.newEmailAddress("test@cormp.com"));
		orderRepository.save(order);

		Optional<Order> savedOrder = orderRepository.findById(order.id());
		assertTrue(savedOrder.isPresent());

		List<Order> findOrdersBetween = orderRepository.findAllOrdersBetween(LocalDateTime.now().minusDays(1),
				LocalDateTime.now().plusDays(1));
		assertNotNull(findOrdersBetween);
	}

}
