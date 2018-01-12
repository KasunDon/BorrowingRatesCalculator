package com.zopa.borrowingratescalculator.domain.compute.rule

import com.zopa.borrowingratescalculator.domain.entity.InterestRate
import com.zopa.borrowingratescalculator.domain.entity.Lender
import com.zopa.borrowingratescalculator.domain.entity.Money
import com.zopa.borrowingratescalculator.domain.exception.ApplicableLenderNotFoundException
import spock.lang.Specification

class TotalBalanceComputeRuleTest extends Specification {

    def lenders = [
        new Lender("lender5", InterestRate.of(BigDecimal.valueOf(5)), Money.of(BigDecimal.valueOf(5000))),
        new Lender("lender4", InterestRate.of(BigDecimal.valueOf(4)), Money.of(BigDecimal.valueOf(3000))),
        new Lender("lender1", InterestRate.of(BigDecimal.valueOf(1)), Money.of(BigDecimal.valueOf(1000))),
        new Lender("lender2", InterestRate.of(BigDecimal.valueOf(2)), Money.of(BigDecimal.valueOf(2000))),
        new Lender("lender3", InterestRate.of(BigDecimal.valueOf(3)), Money.of(BigDecimal.valueOf(3000)))
    ]

    def totalBalanceComputeRule = new TotalBalanceComputeRule()

    def "TotalBalanceComputeRule - should be able to sum up all balances from lenders and return empty optional"() {
        setup:
        def loanAmount = Money.of(10000)

        when:
        def interestRate = totalBalanceComputeRule.compute(loanAmount, lenders)

        then:
        noExceptionThrown()

        interestRate instanceof Optional

        interestRate.isPresent() == false
    }

    def "TotalBalanceComputeRule - should throw an exception when total balance greater than loan amount"() {
        setup:
        def loanAmount = Money.of(15000)

        when:
        totalBalanceComputeRule.compute(loanAmount, lenders)

        then:
        def exception = thrown(ApplicableLenderNotFoundException)
        exception.message == "No satisfiable lenders found."
    }
}
