package com.zopa.borrowingratescalculator.domain.compute;

import com.zopa.borrowingratescalculator.domain.entity.CompoundInterest;
import com.zopa.borrowingratescalculator.domain.entity.InterestRate;
import com.zopa.borrowingratescalculator.domain.entity.Money;

import java.math.BigDecimal;

public class CompoundInterestCalculator {

    public CompoundInterest calculate(
        Money loanAmount,
        InterestRate interestRate,
        long loanTerm
    ) {
        double principalAmount = loanAmount.getAmount().doubleValue();
        double loanRate = interestRate.getRate().doubleValue();

        double totalRepayment = principalAmount * Math.pow(1 + loanRate / 100, loanTerm);

        return new CompoundInterest(
            Money.of(
                BigDecimal.valueOf(totalRepayment)
            )
        );
    }
}
