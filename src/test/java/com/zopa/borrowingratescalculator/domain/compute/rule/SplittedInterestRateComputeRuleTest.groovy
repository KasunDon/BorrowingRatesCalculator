package com.zopa.borrowingratescalculator.domain.compute.rule

import com.zopa.borrowingratescalculator.domain.entity.InterestRate
import com.zopa.borrowingratescalculator.domain.entity.Lender
import com.zopa.borrowingratescalculator.domain.entity.Money
import spock.lang.Specification

class SplittedInterestRateComputeRuleTest extends Specification {

    def lenders = [
        new Lender("lender5", InterestRate.of(BigDecimal.valueOf(5)), Money.of(BigDecimal.valueOf(4000))),
        new Lender("lender4", InterestRate.of(BigDecimal.valueOf(4)), Money.of(BigDecimal.valueOf(4000))),
        new Lender("lender1", InterestRate.of(BigDecimal.valueOf(1)), Money.of(BigDecimal.valueOf(1000))),
        new Lender("lender2", InterestRate.of(BigDecimal.valueOf(2)), Money.of(BigDecimal.valueOf(2000))),
        new Lender("lender3", InterestRate.of(BigDecimal.valueOf(3)), Money.of(BigDecimal.valueOf(3000)))
    ]

    def splittedInterestRateComputeRule = new SplittedInterestRateComputeRule()

    def "SplittedInterestRateComputeRule - should split loan amount across different lenders and calculate combined interest rate - 1"() {
        setup:
        def loanAmount = Money.of(1300.00)

        when:
        def interestRate = splittedInterestRateComputeRule.compute(loanAmount, lenders)

        then:
        interestRate instanceof Optional

        interestRate.get().rate == 1.2307692307692308
    }

    def "SplittedInterestRateComputeRule - should split loan amount across different lenders and calculate combined interest rate - 2"() {
        setup:
        def loanAmount = Money.of(1000.00)

        when:
        def interestRate = splittedInterestRateComputeRule.compute(loanAmount, lenders)

        then:
        interestRate instanceof Optional

        interestRate.get().rate == 1.0
    }

    def "SplittedInterestRateComputeRule - should split loan amount across different lenders and calculate combined interest rate - 3"() {
        setup:
        def loanAmount = Money.of(14100.00)

        when:
        def interestRate = splittedInterestRateComputeRule.compute(loanAmount, lenders)

        then:
        interestRate instanceof Optional

        interestRate.get().rate == 3.546099290780142
    }

    def "SplittedInterestRateComputeRule - should split loan amount across different lenders and calculate combined interest rate - 4"() {
        setup:
        def loanAmount = Money.of(2500.00)

        when:
        def interestRate = splittedInterestRateComputeRule.compute(loanAmount, lenders)

        then:
        interestRate instanceof Optional

        interestRate.get().rate == 1.6
    }

    def "SplittedInterestRateComputeRule - should handle given no lenders"() {
        setup:
        def loanAmount = Money.of(14100.00)

        when:
        def interestRate = splittedInterestRateComputeRule.compute(loanAmount, [])

        then:
        interestRate instanceof Optional

        interestRate.isPresent() == false
    }
}
