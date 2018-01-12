package com.zopa.borrowingratescalculator.domain

import com.zopa.borrowingratescalculator.domain.compute.LoanQuotesGenerator
import com.zopa.borrowingratescalculator.domain.entity.Quote
import com.zopa.borrowingratescalculator.domain.validator.InputDataValidator
import com.zopa.borrowingratescalculator.library.formatter.OutputFormatter
import spock.lang.Specification

class ApplicationExecutorTest extends Specification {

    def inputDataValidator = Mock(InputDataValidator)
    def loanQuotesCalculator = Mock(LoanQuotesGenerator)
    def outputFormatter = Mock(OutputFormatter)

    def applicationExecutor = new ApplicationExecutor(inputDataValidator, loanQuotesCalculator, outputFormatter)

    def "ApplicationExecutor - should be able to handle input parameters"() {
        setup:
        def inputParameters = ["main.class", "/some/location", "100"] as String[]

        when:
        def canExecute = applicationExecutor.canExecute(inputParameters)

        then:
        1 * inputDataValidator.validate(inputParameters)

        canExecute == true

        applicationExecutor.fileLocation == "/some/location"
        applicationExecutor.requestedLoanAmount.amount == 100.0
    }

    def "ApplicationExecutor - should throw an exception when cannot handle input parameters"() {
        setup:
        def inputParameters = ["main.class", "/some/location", "100h"] as String[]

        when:
        applicationExecutor.canExecute(inputParameters)

        then:
        1 * inputDataValidator.validate(inputParameters) >> {
            throw new IllegalArgumentException()
        }

        thrown(IllegalArgumentException)
    }

    def "ApplicationExecutor - should handle empty quote from loan quote generator"() {
        setup:
        def byteArrayOutputStream = new ByteArrayOutputStream()
        def printStream = new PrintStream(byteArrayOutputStream)

        System.out = printStream
        def inputParameters = ["main.class", "/some/location", "100"] as String[]

        when:
        def canExecute = applicationExecutor.canExecute(inputParameters)

        then:
        1 * inputDataValidator.validate(inputParameters)

        canExecute == true

        applicationExecutor.fileLocation == "/some/location"
        applicationExecutor.requestedLoanAmount.amount == 100.0

        when:
        applicationExecutor.execute()

        then:
        1 * loanQuotesCalculator.generate(_) >> Optional.empty()
        0 * outputFormatter.format(_)

        byteArrayOutputStream.toString() == "Sorry!, it is not possible to provide a quote at this time.\n"
    }

    def "ApplicationExecutor - should allow to execute application when everything is under control"() {
        setup:
        def inputParameters = ["main.class", "/some/location", "100"] as String[]

        when:
        def canExecute = applicationExecutor.canExecute(inputParameters)

        then:
        1 * inputDataValidator.validate(inputParameters)

        canExecute == true

        applicationExecutor.fileLocation == "/some/location"
        applicationExecutor.requestedLoanAmount.amount == 100.0

        when:
        applicationExecutor.execute()

        then:
        1 * loanQuotesCalculator.generate(_) >> Optional.of(Mock(Quote))
        1 * outputFormatter.format(_)
    }
}
