package com.zopa.borrowingratescalculator.domain.compute;

import com.zopa.borrowingratescalculator.domain.entity.InterestRate;
import com.zopa.borrowingratescalculator.domain.entity.Lender;
import com.zopa.borrowingratescalculator.domain.entity.Money;
import com.zopa.borrowingratescalculator.domain.exception.ApplicableLenderNotFoundException;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class InterestRateComputeRuleExecutor implements InterestRateComputeRule {

    private final List<InterestRateComputeRule> interestRateComputeRules;

    public InterestRateComputeRuleExecutor(
        List<InterestRateComputeRule> interestRateComputeRules
    ) {
        this.interestRateComputeRules = interestRateComputeRules;
    }

    public Optional<InterestRate> compute(
        Money loanAmount,
        List<Lender> lenders
    ) {
        List<InterestRate> interestRates = new ArrayList<>();

        try {

            for (InterestRateComputeRule interestRateComputeRule : interestRateComputeRules) {

                Optional<InterestRate> interestRate =
                    interestRateComputeRule.compute(loanAmount, lenders);

                interestRate.ifPresent(interestRates::add);
            }

        } catch (ApplicableLenderNotFoundException e) {
            return Optional.empty();
        }

        return interestRates
            .stream()
            .map(InterestRate::getRate)
            .map(rate -> rate.setScale(1, RoundingMode.HALF_UP))
            .map(InterestRate::of)
            .min(Comparator.comparing(InterestRate::getRate));
    }
}
