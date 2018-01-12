package com.zopa.borrowingratescalculator.domain.compute

import com.zopa.borrowingratescalculator.domain.entity.InterestRate
import com.zopa.borrowingratescalculator.domain.entity.Money
import spock.lang.Specification

class CompoundInterestCalculatorTest extends Specification {

    def compoundInterestCalculator = new CompoundInterestCalculator()

    def "CompoundInterestCalculator - should calculate compounding interest for loan amount, interest rate and agreement term"() {
        setup:
        def loanAmount = Mock(Money) {
            getAmount() >> BigDecimal.valueOf(principalAmount)
        }
        def interestRate = Mock(InterestRate) {
            getRate() >> BigDecimal.valueOf(rate)
        }

        when:
        def calculatedCompoundingInterest =
            compoundInterestCalculator.calculate(loanAmount, interestRate, agreementTermInMonths)

        then:
        calculatedCompoundingInterest.repaymentAmount.amount == totalRepayment

        where:
        principalAmount | rate | agreementTermInMonths | totalRepayment
        100             | 10   | 2                     | 121.00
        1400            | 0.22 | 70                    | 1632.81
        13453           | 1.56 | 36                    | 23487.46
        677.34          | 49.9 | 6                     | 7684.52
    }
}
