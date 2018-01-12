package com.zopa.borrowingratescalculator.domain.entity;

public class Quote {

    private Money loanAmount;
    private InterestRate interestRate;
    private Money monthlyRepayment;
    private Money totalRepayment;

    public Quote(
        Money loanAmount,
        InterestRate interestRate,
        Money monthlyRepayment,
        Money totalRepayment
    ) {
        if (loanAmount == null) {
            throw new IllegalArgumentException("loanAmount cannot be null");
        }

        if (interestRate == null) {
            throw new IllegalArgumentException("interestRate cannot be null");
        }

        if (monthlyRepayment == null) {
            throw new IllegalArgumentException("monthlyRepayment cannot be null");
        }

        if (totalRepayment == null) {
            throw new IllegalArgumentException("totalRepayment cannot be null");
        }

        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.monthlyRepayment = monthlyRepayment;
        this.totalRepayment = totalRepayment;
    }

    public Money getLoanAmount() {
        return loanAmount;
    }

    public InterestRate getInterestRate() {
        return interestRate;
    }

    public Money getMonthlyRepayment() {
        return monthlyRepayment;
    }

    public Money getTotalRepayment() {
        return totalRepayment;
    }
}
