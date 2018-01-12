package com.zopa.borrowingratescalculator.domain.entity;

public class CompoundInterest {

    private Money repaymentAmount;

    public CompoundInterest(
        Money repaymentAmount
    ) {
        if (repaymentAmount == null) {
            throw new IllegalArgumentException("repaymentAmount cannot be null");
        }

        this.repaymentAmount = repaymentAmount;
    }

    public Money getRepaymentAmount() {
        return repaymentAmount;
    }
}
