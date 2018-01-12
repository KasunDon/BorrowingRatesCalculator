package com.zopa.borrowingratescalculator.domain.entity;

import java.math.BigDecimal;

public class InterestRate {

    private BigDecimal rate;

    public InterestRate(
        BigDecimal rate
    ) {
        if (rate == null) {
            throw new IllegalArgumentException("rate cannot be null");
        }

        this.rate = rate;
    }

    public static InterestRate of(
        BigDecimal rate
    ) {
        return new InterestRate(rate);
    }

    public BigDecimal getRate() {
        return rate;
    }
}
