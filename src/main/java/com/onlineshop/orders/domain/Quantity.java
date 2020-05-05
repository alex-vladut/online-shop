package com.onlineshop.orders.domain;

import com.onlineshop.core.ValidationException;
import lombok.EqualsAndHashCode;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.math.BigDecimal;

@EqualsAndHashCode
@UserDefinedType("quantity")
public class Quantity {
    private final int value;

    private Quantity(final int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public BigDecimal asBigDecimal() {
        return BigDecimal.valueOf(value());
    }

    public static Quantity newQuantity(final int value) {
        if (value <= 0) {
            throw new ValidationException("quantity", "Quantity must be positive");
        }
        if (value > 240) {
            throw new ValidationException("quantity", "Quantity must be less than 240.");
        }
        return new Quantity(value);
    }
}
