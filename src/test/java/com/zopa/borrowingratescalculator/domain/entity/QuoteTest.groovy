package com.zopa.borrowingratescalculator.domain.entity

import spock.lang.Specification

class QuoteTest extends Specification {

    def "Quote - should hold constructed data"() {
        setup:
        def loanAmount = Mock(Money) {
            getAmount() >> BigDecimal.TEN
        }
        def interestRate = Mock(InterestRate) {
            getRate() >> BigDecimal.ONE
        }
        def monthlyRepayment = Mock(Money) {
            getAmount() >> 1.10
        }
        def totalRepayment = Mock(Money) {
            getAmount() >> 11.10
        }

        def quote = new Quote(
            loanAmount,
            interestRate,
            monthlyRepayment,
            totalRepayment
        )

        expect:
        quote.loanAmount.amount == loanAmount.amount
        quote.interestRate.rate == interestRate.rate
        quote.monthlyRepayment.amount == monthlyRepayment.amount
        quote.totalRepayment.amount == totalRepayment.amount
    }

    def "Quote - should throw an exception when loanAmount is null"() {
        setup:
        def loanAmount = null
        def interestRate = Mock(InterestRate) {
            getRate() >> BigDecimal.ONE
        }
        def monthlyRepayment = Mock(Money) {
            getAmount() >> 1.10
        }
        def totalRepayment = Mock(Money) {
            getAmount() >> 11.10
        }

        when:
        new Quote(
            loanAmount,
            interestRate,
            monthlyRepayment,
            totalRepayment
        )

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "loanAmount cannot be null"
    }

    def "Quote - should throw an exception when interestRate is null"() {
        setup:
        def loanAmount = Mock(Money) {
            getAmount() >> BigDecimal.TEN
        }
        def interestRate = null
        def monthlyRepayment = Mock(Money) {
            getAmount() >> 1.10
        }
        def totalRepayment = Mock(Money) {
            getAmount() >> 11.10
        }

        when:
        new Quote(
            loanAmount,
            interestRate,
            monthlyRepayment,
            totalRepayment
        )

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "interestRate cannot be null"
    }

    def "Quote - should throw an exception when monthlyRepayment is null"() {
        setup:
        def loanAmount = Mock(Money) {
            getAmount() >> BigDecimal.TEN
        }
        def interestRate = Mock(InterestRate) {
            getRate() >> BigDecimal.ONE
        }
        def monthlyRepayment = null
        def totalRepayment = Mock(Money) {
            getAmount() >> 11.10
        }

        when:
        new Quote(
            loanAmount,
            interestRate,
            monthlyRepayment,
            totalRepayment
        )

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "monthlyRepayment cannot be null"
    }

    def "Quote - should throw an exception when totalRepayment is null"() {
        setup:
        def loanAmount = Mock(Money) {
            getAmount() >> BigDecimal.TEN
        }
        def interestRate = Mock(InterestRate) {
            getRate() >> BigDecimal.ONE
        }
        def monthlyRepayment = Mock(Money) {
            getAmount() >> 1.10
        }
        def totalRepayment = null

        when:
        new Quote(
            loanAmount,
            interestRate,
            monthlyRepayment,
            totalRepayment
        )

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "totalRepayment cannot be null"
    }
}
