package com.zopa.borrowingratescalculator.domain.entity

import spock.lang.Specification

class MoneyTest extends Specification {

    def "Money - should hold constructed data"() {
        setup:
        def amount = BigDecimal.ONE

        def money = new Money(amount)

        expect:
        money.amount == amount
    }

    def "Money - should throw an exception when amount is null"() {
        setup:
        def amount = null

        when:
        new Money(amount)

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "amount cannot be null"
    }


    def "Money - lets you create instance of itself with 2 decimal scale"() {
        setup:
        def amount = 1.111

        when:
        def money = Money.of(amount)

        then:
        noExceptionThrown()

        money.amount == 1.11
    }
}
