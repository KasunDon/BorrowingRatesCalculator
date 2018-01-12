package com.zopa.borrowingratescalculator.domain.entity

import spock.lang.Specification

class CompoundInterestTest extends Specification {

    def "CompoundInterest - should hold constructed data"() {
        setup:
        def repaymentAmount = Mock(Money) {
            getAmount() >> BigDecimal.valueOf(1)
        }

        def compoundInterest = new CompoundInterest(repaymentAmount)

        expect:
        compoundInterest.repaymentAmount.amount == repaymentAmount.amount
    }

    def "CompoundInterest - should throw an exception when repaymentAmount is null"() {
        setup:
        def repaymentAmount = null

        when:
        new CompoundInterest(repaymentAmount)

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "repaymentAmount cannot be null"
    }
}
