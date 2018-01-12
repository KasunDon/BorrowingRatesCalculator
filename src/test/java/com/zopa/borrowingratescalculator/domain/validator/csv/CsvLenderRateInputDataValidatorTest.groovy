package com.zopa.borrowingratescalculator.domain.validator.csv

import spock.lang.Specification

class CsvLenderRateInputDataValidatorTest extends Specification {

    def csvLenderRateInputDataValidator = new CsvLenderRateInputDataValidator()

    def "CsvLenderRateInputDataValidator - should throw an exception when rate is missing"() {
        setup:
        def data = ["lender", rate] as String[]

        when:
        csvLenderRateInputDataValidator.validate(data)

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "interest rate is missing."

        where:
        rate | _
        null | _
        ""   | _
    }

    def "CsvLenderRateInputDataValidator - should throw an exception when rate is not a number"() {
        setup:
        def data = ["1", "1.2a0"] as String[]

        when:
        csvLenderRateInputDataValidator.validate(data)

        then:
        def exception = thrown(NumberFormatException)
        exception.message == "interest rate is not a number."
    }

    def "CsvLenderRateInputDataValidator - should not throw an exception when rate is valid"() {
        setup:
        def data = ["1", "1.20"] as String[]

        when:
        csvLenderRateInputDataValidator.validate(data)

        then:
        notThrown()
    }
}
