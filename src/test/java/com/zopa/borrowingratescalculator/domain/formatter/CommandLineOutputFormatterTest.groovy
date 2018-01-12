package com.zopa.borrowingratescalculator.domain.formatter

import com.zopa.borrowingratescalculator.domain.entity.InterestRate
import com.zopa.borrowingratescalculator.domain.entity.Money
import com.zopa.borrowingratescalculator.domain.entity.Quote
import spock.lang.Specification

class CommandLineOutputFormatterTest extends Specification {

    def commandLineOutputFormatter = new CommandLineOutputFormatter()

    def "CommandLineOutputFormatter - it should output to command line with desired format"() {
        setup:
        def quote = new Quote(
            Money.of(100.00),
            InterestRate.of(1.2),
            Money.of(3.33),
            Money.of(10.00)
        )

        def byteArrayOutputStream = new ByteArrayOutputStream()
        def printStream = new PrintStream(byteArrayOutputStream)

        System.out = printStream

        when:
        commandLineOutputFormatter.format(quote)

        then:
        byteArrayOutputStream.toString() ==

        "Requested amount: £100.00\n" +
        "Rate: 1.2%\n" +
        "Monthly repayment: £3.33\n" +
        "Total repayment: £10.00\n"
    }
}
