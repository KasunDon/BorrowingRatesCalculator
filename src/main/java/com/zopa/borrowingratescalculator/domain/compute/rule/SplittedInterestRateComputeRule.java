package com.zopa.borrowingratescalculator.domain.compute.rule;

import com.zopa.borrowingratescalculator.domain.compute.InterestRateComputeRule;
import com.zopa.borrowingratescalculator.domain.entity.InterestRate;
import com.zopa.borrowingratescalculator.domain.entity.Lender;
import com.zopa.borrowingratescalculator.domain.entity.Money;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SplittedInterestRateComputeRule implements InterestRateComputeRule {

    public Optional<InterestRate> compute(
        Money loanAmount,
        List<Lender> lenders
    ) {
        lenders.sort(Comparator.comparing(lender -> lender.getInterestRate().getRate()));

        double monthlyPayment = 0;

        double requestedLoanAmount = loanAmount.getAmount().doubleValue();

        double remainingBalance = requestedLoanAmount;

        for (Lender lender : lenders) {

            if (remainingBalance == 0) {
                break;
            }

            double lenderBalance = lender.getBalance().getAmount().doubleValue();
            double interestRate = lender.getInterestRate().getRate().doubleValue();

            if (lenderBalance <= remainingBalance) {
                monthlyPayment += calculateMonthlyPayment(
                    lenderBalance,
                    interestRate
                );
                remainingBalance -= lenderBalance;
                continue;
            }

            if (remainingBalance <= lenderBalance) {
                monthlyPayment += calculateMonthlyPayment(remainingBalance, interestRate);
                remainingBalance -= remainingBalance;
            }
        }

        double newInterestRate = (monthlyPayment / requestedLoanAmount) * 100;

        if (newInterestRate == 0) {
            return Optional.empty();
        }

        return Optional.of(
            InterestRate.of(
                BigDecimal.valueOf(newInterestRate)
            )
        );
    }

    private double calculateMonthlyPayment(
        double borrowedAmount,
        double interestRate
    ) {
        return (borrowedAmount * interestRate) / 100;
    }

}
