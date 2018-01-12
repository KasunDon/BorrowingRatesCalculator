#!/usr/bin/env bash

java -D"spring.profiles.active=production" -jar target/borrowingratescalculator.jar \
com.zopa.borrowingratescalculator.BorrowingRatesCalculatorApp "$@"