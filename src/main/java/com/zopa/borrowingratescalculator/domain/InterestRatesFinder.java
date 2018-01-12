package com.zopa.borrowingratescalculator.domain;

import com.zopa.borrowingratescalculator.domain.compute.InterestRateComputeRuleExecutor;
import com.zopa.borrowingratescalculator.domain.entity.InterestRate;
import com.zopa.borrowingratescalculator.domain.entity.Lender;
import com.zopa.borrowingratescalculator.domain.entity.Money;

import java.util.List;
import java.util.Optional;

public class InterestRatesFinder {

    private final InterestRateComputeRuleExecutor interestRateComputeRuleExecutor;
    private List<Lender> lenders;
    private Money loanAmount;

    public InterestRatesFinder(
        InterestRateComputeRuleExecutor interestRateComputeRuleExecutor
    ) {
        this.interestRateComputeRuleExecutor = interestRateComputeRuleExecutor;
    }

    public InterestRatesFinder addLenders(
        List<Lender> lenders
    ) {
        if (lenders == null) {
            throw new IllegalArgumentException("lenders cannot be null.");
        }

        this.lenders = lenders;

        return this;
    }

    public InterestRatesFinder addLoanAmount(
        Money loanAmount
    ) {
        if (loanAmount == null) {
            throw new IllegalArgumentException("loanAmount cannot be null.");
        }

        this.loanAmount = loanAmount;

        return this;
    }

    public Optional<InterestRate> find() {
        if (lenders == null) {
            throw new IllegalArgumentException("lenders cannot be null.");
        }

        if (loanAmount == null) {
            throw new IllegalArgumentException("loanAmount cannot be null.");
        }

        return interestRateComputeRuleExecutor
            .compute(
                loanAmount,
                lenders
            );
    }

}
