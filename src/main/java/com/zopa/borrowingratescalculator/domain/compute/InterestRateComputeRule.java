package com.zopa.borrowingratescalculator.domain.compute;

import com.zopa.borrowingratescalculator.domain.entity.InterestRate;
import com.zopa.borrowingratescalculator.domain.entity.Lender;
import com.zopa.borrowingratescalculator.domain.entity.Money;

import java.util.List;
import java.util.Optional;

public interface InterestRateComputeRule {

    Optional<InterestRate> compute(
        Money loanAmount,
        List<Lender> lenders
    );
}
