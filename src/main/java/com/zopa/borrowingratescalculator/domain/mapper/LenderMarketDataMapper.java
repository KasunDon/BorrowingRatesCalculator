package com.zopa.borrowingratescalculator.domain.mapper;

import com.zopa.borrowingratescalculator.domain.entity.InterestRate;
import com.zopa.borrowingratescalculator.domain.entity.Lender;
import com.zopa.borrowingratescalculator.domain.entity.Money;

import java.math.BigDecimal;

public class LenderMarketDataMapper {

    public Lender map(
        String[] data
    ) {
        if (data.length < 3) {
            throw new IllegalArgumentException("mapping source data doesn't contain required number of elements.");
        }

        BigDecimal rate = BigDecimal.valueOf(
            Double.valueOf(data[1])
        );

        BigDecimal availableAmount = BigDecimal.valueOf(
            Double.valueOf(data[2])
        );

        return new Lender(
            data[0],
            InterestRate.of(rate),
            Money.of(availableAmount)
        );
    }
}
