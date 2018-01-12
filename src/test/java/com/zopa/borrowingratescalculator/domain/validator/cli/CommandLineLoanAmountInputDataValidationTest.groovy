package com.zopa.borrowingratescalculator.domain.validator.cli

import spock.lang.Specification

class CommandLineLoanAmountInputDataValidationTest extends Specification {

    def commandLineLoanAmountInputDataValidation = new CommandLineLoanAmountInputDataValidation()

    def "CommandLineLoanAmountInputDataValidation - should throw an exception when loan amount is missing"() {
        setup:
        def data = ["main.class", "/some/location", loanAmount] as String[]

        when:
        commandLineLoanAmountInputDataValidation.validate(data)

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "second command-line argument, loan amount is missing."

        where:
        loanAmount | _
        null       | _
        ""         | _
    }

    def "CommandLineLoanAmountInputDataValidation - should throw an exception when loan amount is not a number"() {
        setup:
        def data = ["main.class", "/some/location", "1ab"] as String[]

        when:
        commandLineLoanAmountInputDataValidation.validate(data)

        then:
        def exception = thrown(NumberFormatException)
        exception.message == "loan amount is not a number."
    }

    def "CommandLineLoanAmountInputDataValidation - should not throw an exception when loan amount is valid"() {
        setup:
        def data = ["main.class", "/some/location", "1000.50"] as String[]

        when:
        commandLineLoanAmountInputDataValidation.validate(data)

        then:
        notThrown()
    }

}
