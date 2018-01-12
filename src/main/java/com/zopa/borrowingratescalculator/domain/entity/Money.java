package com.zopa.borrowingratescalculator.domain.entity;

import java.math.BigDecimal;

public class Money {

    private BigDecimal amount;

    public Money(
        BigDecimal amount
    ) {
        if (amount == null) {
            throw new IllegalArgumentException("amount cannot be null");
        }

        this.amount = amount;
    }

    public static Money of(
        BigDecimal amount
    ) {
        amount = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
        return new Money(amount);
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
