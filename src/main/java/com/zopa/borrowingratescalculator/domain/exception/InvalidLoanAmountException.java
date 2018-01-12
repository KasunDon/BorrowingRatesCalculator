package com.zopa.borrowingratescalculator.domain.exception;

public class InvalidLoanAmountException extends RuntimeException {

    public InvalidLoanAmountException(String message) {
        super(message);
    }

}
