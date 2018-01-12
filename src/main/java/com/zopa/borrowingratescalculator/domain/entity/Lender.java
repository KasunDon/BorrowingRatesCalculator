package com.zopa.borrowingratescalculator.domain.entity;

public class Lender {

    private String name;
    private InterestRate interestRate;
    private Money balance;

    public Lender(
        String name,
        InterestRate interestRate,
        Money balance
    ) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name cannot be null or empty.");
        }

        if (interestRate == null) {
            throw new IllegalArgumentException("interestRate cannot be null.");
        }

        if (balance == null) {
            throw new IllegalArgumentException("balance cannot be null.");
        }

        this.name = name;
        this.interestRate = interestRate;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public InterestRate getInterestRate() {
        return interestRate;
    }

    public Money getBalance() {
        return balance;
    }
}
