package com.zopa.borrowingratescalculator.domain.validator.csv

import spock.lang.Specification

class CsvLenderNameInputDataValidatorTest extends Specification {

    def csvLenderNameInputDataValidator = new CsvLenderNameInputDataValidator()

    def "CsvLenderNameInputDataValidator - should throw an exception when lender name is missing"() {
        setup:
        def data = [lenderName] as String[]

        when:
        csvLenderNameInputDataValidator.validate(data)

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "lender name is missing."

        where:
        lenderName | _
        null       | _
        ""         | _
    }

    def "CsvLenderNameInputDataValidator - should not throw an exception when lender name is present"() {
        setup:
        def data = ["lender1"] as String[]

        when:
        csvLenderNameInputDataValidator.validate(data)

        then:
        notThrown()
    }
}
