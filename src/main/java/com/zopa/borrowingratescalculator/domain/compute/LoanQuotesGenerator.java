package com.zopa.borrowingratescalculator.domain.compute;

import com.zopa.borrowingratescalculator.domain.InterestRatesFinder;
import com.zopa.borrowingratescalculator.domain.entity.CompoundInterest;
import com.zopa.borrowingratescalculator.domain.entity.InterestRate;
import com.zopa.borrowingratescalculator.domain.entity.Money;
import com.zopa.borrowingratescalculator.domain.entity.Quote;
import com.zopa.borrowingratescalculator.domain.exception.InvalidLoanAmountException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class LoanQuotesGenerator {

    private static final int DEFAULT_LOAN_TERM_IN_MONTHS = 36;

    private final InterestRatesFinder interestRatesFinder;
    private final CompoundInterestCalculator compoundInterestCalculator;

    public LoanQuotesGenerator(
        InterestRatesFinder interestRatesFinder,
        CompoundInterestCalculator compoundInterestCalculator
    ) {
        this.interestRatesFinder = interestRatesFinder;
        this.compoundInterestCalculator = compoundInterestCalculator;
    }

    public Optional<Quote> generate(
        BigDecimal loanAmount
    ) {

        verifyLoanAmount(
            loanAmount.doubleValue()
        );

        Optional<InterestRate> matchedInterestRate =
            interestRatesFinder.find();

        if (!matchedInterestRate
            .isPresent()) {
            return Optional.empty();
        }

        InterestRate interestRate = matchedInterestRate.get();

        CompoundInterest compoundInterest =
            compoundInterestCalculator
                .calculate(
                    Money.of(loanAmount),
                    interestRate,
                    DEFAULT_LOAN_TERM_IN_MONTHS
                );

        BigDecimal totalRepayments =
            compoundInterest
                .getRepaymentAmount()
                .getAmount();


        BigDecimal monthlyRePayments =
            totalRepayments
                .divide(
                    BigDecimal.valueOf(DEFAULT_LOAN_TERM_IN_MONTHS),
                    2,
                    RoundingMode.HALF_UP
                );


        Quote quote = new Quote(
            Money.of(loanAmount),
            interestRate,
            Money.of(monthlyRePayments),
            Money.of(totalRepayments)
        );

        return Optional.of(quote);
    }

    private void verifyLoanAmount(
        double loanAmount
    ) {
        if (!(loanAmount >= 1000 && loanAmount <= 15000)) {
            throw new InvalidLoanAmountException("loan amount should be between 1000-1500");
        }

        if (loanAmount % 100 != 0) {
            throw new InvalidLoanAmountException("loan amount should be increments of 100");
        }
    }
}
