package com.zopa.borrowingratescalculator.domain.compute.rule;

import com.zopa.borrowingratescalculator.domain.compute.InterestRateComputeRule;
import com.zopa.borrowingratescalculator.domain.entity.InterestRate;
import com.zopa.borrowingratescalculator.domain.entity.Lender;
import com.zopa.borrowingratescalculator.domain.entity.Money;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class LenderWithLowInterestRateComputeRule implements InterestRateComputeRule {

    public Optional<InterestRate> compute(
        Money loanAmount,
        List<Lender> lenders
    ) {
        lenders.sort(Comparator.comparing(lender -> lender.getInterestRate().getRate()));

        Optional<Lender> optionalLender =
            lenders
                .stream()
                .filter(lender -> canLenderCoverLoanAmount(loanAmount, lender))
                .findFirst();

        return optionalLender.map(Lender::getInterestRate);
    }

    private boolean canLenderCoverLoanAmount(
        Money loanAmount,
        Lender lender
    ) {
        return lender.getBalance().getAmount().doubleValue() >= loanAmount.getAmount().doubleValue();
    }
}
