package com.zopa.borrowingratescalculator.domain.compute.rule;

import com.zopa.borrowingratescalculator.domain.compute.InterestRateComputeRule;
import com.zopa.borrowingratescalculator.domain.entity.InterestRate;
import com.zopa.borrowingratescalculator.domain.entity.Lender;
import com.zopa.borrowingratescalculator.domain.entity.Money;
import com.zopa.borrowingratescalculator.domain.exception.ApplicableLenderNotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class TotalBalanceComputeRule implements InterestRateComputeRule {

    public Optional<InterestRate> compute(
        Money loanAmount,
        List<Lender> lenders
    ) {
        BigDecimal totalAmountAvailable =
            lenders
                .stream()
                .map(Lender::getBalance)
                .map(Money::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (isLoanAmountGreaterThanTotalBalance(loanAmount, totalAmountAvailable)) {
            throw new ApplicableLenderNotFoundException("No satisfiable lenders found.");
        }

        return Optional.empty();
    }

    private boolean isLoanAmountGreaterThanTotalBalance(
        Money loanAmount,
        BigDecimal totalBalance
    ) {
        return totalBalance.doubleValue() < loanAmount.getAmount().doubleValue();
    }
}
