package com.onlineshop.domain.order;

import com.onlineshop.core.domain.Money;
import com.onlineshop.orders.domain.EmailAddress;
import com.onlineshop.orders.domain.Order;
import com.onlineshop.orders.domain.Quantity;
import com.onlineshop.products.domain.Product;
import org.junit.Test;

import java.math.BigDecimal;

import static com.onlineshop.core.domain.Money.DEFAULT_CURRENCY;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class OrderTest {

    @Test
    public void shouldCreateOrder_withProducts() {
        final Product product = Product.newProduct("Nike Air",
                Money.newMoney(DEFAULT_CURRENCY, new BigDecimal("100.00")));
        final EmailAddress buyerEmailAddress = EmailAddress.newEmailAddress("customer@comp.org");

        final Order order = Order.newEmptyOrder(buyerEmailAddress);

        assertNotNull(order);
        assertNotNull(order.id());
        assertNotNull(order.creationDateTime());
        assertThat(order.buyerEmailAddress(), is(buyerEmailAddress));
    }

    @Test
    public void shouldCalculateTotalOrderPrice() {
        final Product product1 = Product.newProduct("Nike Air",
                Money.newMoney(DEFAULT_CURRENCY, new BigDecimal("120.00")));
        final Product product2 = Product.newProduct("Adidas t-shirt",
                Money.newMoney(DEFAULT_CURRENCY, new BigDecimal("250.99")));
        final Product product3 = Product.newProduct("Puma perfume",
                Money.newMoney(DEFAULT_CURRENCY, new BigDecimal("500.10")));
        final EmailAddress buyerEmailAddress = EmailAddress.newEmailAddress("customer@comp.org");
        final Order order = Order.newEmptyOrder(buyerEmailAddress);
        order.addOrderItem(product1, Quantity.newQuantity(1));
        order.addOrderItem(product2, Quantity.newQuantity(1));
        order.addOrderItem(product3, Quantity.newQuantity(1));

        final Money expectedTotalPrice = Money.newMoney(DEFAULT_CURRENCY, new BigDecimal("871.09"));

        final Money totalPrice = order.totalPrice();

        assertThat(totalPrice, is(expectedTotalPrice));
    }

}
