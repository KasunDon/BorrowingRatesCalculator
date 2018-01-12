package com.zopa.borrowingratescalculator.domain

import com.zopa.borrowingratescalculator.domain.compute.InterestRateComputeRuleExecutor
import com.zopa.borrowingratescalculator.domain.entity.InterestRate
import com.zopa.borrowingratescalculator.domain.entity.Lender
import com.zopa.borrowingratescalculator.domain.entity.Money
import spock.lang.Specification

class InterestRatesFinderTest extends Specification {

    def interestRateComputeRuleExecutor = Mock(InterestRateComputeRuleExecutor)
    def interestRatesFinder = new InterestRatesFinder(interestRateComputeRuleExecutor)

    def lenders = [
        new Lender("lender1", InterestRate.of(1.13), Money.of(1410)),
    ]

    def "InterestRatesFinder - should throw an exception when lenders not set and trying find rate"() {
        setup:
        def loanAmount = 10.00
        interestRatesFinder.addLoanAmount(Money.of(loanAmount))

        when:
        interestRatesFinder.find()

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "lenders cannot be null."

        when:
        interestRatesFinder.addLenders(null)

        then:
        def exception2 = thrown(IllegalArgumentException)
        exception2.message == "lenders cannot be null."
    }

    def "InterestRatesFinder - should throw an exception when loan amount not set and trying find rate"() {
        setup:
        interestRatesFinder.addLenders(lenders)

        when:
        interestRatesFinder.find()

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "loanAmount cannot be null."

        when:
        interestRatesFinder.addLoanAmount(null)

        then:
        def exception2 = thrown(IllegalArgumentException)
        exception2.message == "loanAmount cannot be null."
    }

    def "InterestRatesFinder - should find interest rate for requested loan amount"() {
        setup:
        def loanAmount = 1100.00
        interestRatesFinder.addLoanAmount(Money.of(loanAmount))
        interestRatesFinder.addLenders(lenders)

        when:
        def interestRate = interestRatesFinder.find()

        then:
        interestRateComputeRuleExecutor.compute(_, _) >> Optional.of(InterestRate.of(1.13))

        interestRate instanceof Optional

        interestRate.get().rate == 1.13
    }

    def "InterestRatesFinder - should return optional empty when cannot find interest rate for requested loan amount"() {
        setup:
        def loanAmount = 1500.00
        interestRatesFinder.addLoanAmount(Money.of(loanAmount))
        interestRatesFinder.addLenders(lenders)

        when:
        def interestRate = interestRatesFinder.find()

        then:
        interestRateComputeRuleExecutor.compute(_, _) >> Optional.empty()

        interestRate instanceof Optional

        interestRate.isPresent() == false
    }
}
