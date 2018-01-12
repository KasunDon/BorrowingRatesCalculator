package com.zopa.borrowingratescalculator.domain.entity

import spock.lang.Specification

class InterestRateTest extends Specification {

    def "InterestRate - should hold constructed data"() {
        setup:
        def rate = BigDecimal.ONE

        def interestRate = new InterestRate(rate)

        expect:
        interestRate.rate == rate
    }

    def "InterestRate - should throw an exception when rate is null"() {
        setup:
        def rate = null

        when:
        new InterestRate(rate)

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "rate cannot be null"
    }

    def "InterestRate - lets you create instance of itself"() {
        setup:
        def rate = BigDecimal.valueOf(1.111)

        when:
        def interestRate = InterestRate.of(rate)

        then:
        noExceptionThrown()

        interestRate.rate == 1.111
    }
}
