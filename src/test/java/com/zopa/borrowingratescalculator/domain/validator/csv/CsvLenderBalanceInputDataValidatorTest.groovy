package com.zopa.borrowingratescalculator.domain.validator.csv

import spock.lang.Specification

class CsvLenderBalanceInputDataValidatorTest extends Specification {

    def csvLenderBalanceInputDataValidator = new CsvLenderBalanceInputDataValidator()

    def "CsvLenderBalanceInputDataValidator - should throw an exception when loan amount is missing"() {
        setup:
        def data = ["1", "1.20", availableBalance] as String[]

        when:
        csvLenderBalanceInputDataValidator.validate(data)

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "available balance is missing."

        where:
        availableBalance | _
        null             | _
        ""               | _
    }

    def "CsvLenderBalanceInputDataValidator - should throw an exception when available balance is not a number"() {
        setup:
        def data = ["1", "1.20", "1s000"] as String[]

        when:
        csvLenderBalanceInputDataValidator.validate(data)

        then:
        def exception = thrown(NumberFormatException)
        exception.message == "available balance is not a number."
    }

    def "CsvLenderBalanceInputDataValidator - should not throw an exception when loan amount is valid"() {
        setup:
        def data = ["1", "1.20", "1000"] as String[]

        when:
        csvLenderBalanceInputDataValidator.validate(data)

        then:
        notThrown()
    }
}
