package com.onlineshop.domain.order;

import static com.onlineshop.core.domain.Money.DEFAULT_CURRENCY;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import com.onlineshop.orders.domain.Quantity;
import org.junit.Test;

import com.onlineshop.core.domain.Money;
import com.onlineshop.products.domain.Product;
import com.onlineshop.orders.domain.OrderItem;

public class OrderItemTest {

    @Test
    public void shouldCreateOrderItem() {
        final Product product = Product.newProduct("Nike Air",
                Money.newMoney(DEFAULT_CURRENCY, new BigDecimal("100.00")));

        final OrderItem orderItem = OrderItem.newOrderItem(product, Quantity.newQuantity(1));

        assertNotNull(orderItem);
        assertThat(orderItem.productId(), is(product.id()));
        assertThat(orderItem.price(), is(product.price()));
    }

}
