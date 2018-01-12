package com.zopa.borrowingratescalculator.domain.compute.rule

import com.zopa.borrowingratescalculator.domain.entity.InterestRate
import com.zopa.borrowingratescalculator.domain.entity.Lender
import com.zopa.borrowingratescalculator.domain.entity.Money
import spock.lang.Specification

class LenderWithLowInterestRateComputeRuleTest extends Specification {

    def lenders = [
        new Lender("lender5", InterestRate.of(BigDecimal.valueOf(5)), Money.of(BigDecimal.valueOf(5000))),
        new Lender("lender4", InterestRate.of(BigDecimal.valueOf(4)), Money.of(BigDecimal.valueOf(4000))),
        new Lender("lender1", InterestRate.of(BigDecimal.valueOf(1)), Money.of(BigDecimal.valueOf(1000))),
        new Lender("lender2", InterestRate.of(BigDecimal.valueOf(2)), Money.of(BigDecimal.valueOf(2000))),
        new Lender("lender3", InterestRate.of(BigDecimal.valueOf(3)), Money.of(BigDecimal.valueOf(3000)))
    ]

    def lenderWithLowInterestRateComputeRule = new LenderWithLowInterestRateComputeRule()

    def "LenderWithLowInterestRateComputeRule - should find a single lender to cover full loan and return their interest rate"() {
        setup:
        def loanAmount = Money.of(1000.00)

        when:
        def interestRate = lenderWithLowInterestRateComputeRule.compute(loanAmount, lenders)

        then:
        interestRate instanceof Optional

        interestRate.get().rate == 1
    }

    def "LenderWithLowInterestRateComputeRule - should return optional empty when cannot find a lender to cover the loan"() {
        setup:
        def loanAmount = Money.of(7000.00)

        when:
        def interestRate = lenderWithLowInterestRateComputeRule.compute(loanAmount, lenders)

        then:
        interestRate instanceof Optional
        interestRate.isPresent() == false
    }
}
