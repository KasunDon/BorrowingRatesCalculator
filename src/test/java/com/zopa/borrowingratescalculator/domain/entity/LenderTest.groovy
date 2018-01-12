package com.zopa.borrowingratescalculator.domain.entity

import spock.lang.Specification

class LenderTest extends Specification {

    def "Lender - should hold constructed data"() {
        setup:
        def name = "ABC"
        def interestRate = Mock(InterestRate) {
            getRate() >> BigDecimal.ONE
        }
        def balance = Mock(Money) {
            getAmount() >> BigDecimal.TEN
        }

        def lender = new Lender(name, interestRate, balance)

        expect:
        lender.name == name
        lender.interestRate.rate == interestRate.rate
        lender.balance.amount == balance.amount
    }

    def "Lender - should throw an exception when name is null or empty"() {
        setup:
        def interestRate = Mock(InterestRate) {
            getRate() >> BigDecimal.ONE
        }
        def balance = Mock(Money) {
            getAmount() >> BigDecimal.TEN
        }

        when:
        new Lender(name, interestRate, balance)

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "name cannot be null or empty."

        where:
        name | _
        null | _
        ""   | _
    }

    def "Lender - should throw an exception when interestRate is null"() {
        setup:
        def name = "ABC"
        def interestRate = null
        def balance = Mock(Money) {
            getAmount() >> BigDecimal.TEN
        }

        when:
        new Lender(name, interestRate, balance)

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "interestRate cannot be null."
    }

    def "Lender - should throw an exception when balance is null"() {
        setup:
        def name = "ABC"
        def interestRate = Mock(InterestRate) {
            getRate() >> BigDecimal.ONE
        }
        def balance = null

        when:
        new Lender(name, interestRate, balance)

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "balance cannot be null."
    }
}
