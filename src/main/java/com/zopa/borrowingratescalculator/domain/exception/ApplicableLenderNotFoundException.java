package com.zopa.borrowingratescalculator.domain.exception;

public class ApplicableLenderNotFoundException extends RuntimeException {

    public ApplicableLenderNotFoundException(String message) {
        super(message);
    }

}
