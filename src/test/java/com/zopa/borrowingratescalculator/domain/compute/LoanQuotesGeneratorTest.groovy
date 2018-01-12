package com.zopa.borrowingratescalculator.domain.compute

import com.zopa.borrowingratescalculator.domain.InterestRatesFinder
import com.zopa.borrowingratescalculator.domain.entity.InterestRate
import com.zopa.borrowingratescalculator.domain.exception.InvalidLoanAmountException
import spock.lang.Specification

class LoanQuotesGeneratorTest extends Specification {

    def interestRatesFinder = Mock(InterestRatesFinder)
    def compoundInterestCalculator = new CompoundInterestCalculator()

    def loanQuotesGenerator = new LoanQuotesGenerator(interestRatesFinder, compoundInterestCalculator)

    def "LoanQuotesGenerator - should throw an exception for loan amounts outside of 1000 - 15000"() {
        setup:
        def loanAmount = BigDecimal.valueOf(requestedAmount)

        when:
        loanQuotesGenerator.generate(loanAmount)

        then:
        def exception = thrown(InvalidLoanAmountException)
        exception.message == message

        where:
        requestedAmount | message
        99900           | "loan amount should be between 1000-1500"
        16000           | "loan amount should be between 1000-1500"
        100             | "loan amount should be between 1000-1500"
    }

    def "LoanQuotesGenerator - should not throw an exception for loan amounts within 1000 - 1500"() {

        when:
        loanQuotesGenerator.generate(requestedAmount)

        then:
        interestRatesFinder.find() >> Optional.empty()
        notThrown()

        where:
        requestedAmount | _
        1000            | _
        1300            | _
        1100            | _
    }

    def "LoanQuotesGenerator - should throw an exception when loan amounts not increments of 100"() {
        setup:
        def loanAmount = BigDecimal.valueOf(requestedAmount)

        when:
        loanQuotesGenerator.generate(loanAmount)

        then:
        def exception = thrown(InvalidLoanAmountException)
        exception.message == message

        where:
        requestedAmount | message
        1101            | "loan amount should be increments of 100"
        1450            | "loan amount should be increments of 100"
        10050           | "loan amount should be increments of 100"
    }

    def "LoanQuotesGenerator - should generate quotes for requested loan amount"() {
        setup:
        def loanAmount = 1100.00

        when:
        def quote = loanQuotesGenerator.generate(loanAmount)

        then:
        interestRatesFinder.find() >> Optional.of(InterestRate.of(0.1))

        quote instanceof Optional

        quote.get().loanAmount.amount == loanAmount
        quote.get().interestRate.rate == 0.1
        quote.get().totalRepayment.amount == 1140.30
        quote.get().monthlyRepayment.amount == 31.68
    }
}
