package com.zopa.borrowingratescalculator.domain.validator.cli

import spock.lang.Specification

class CommandLineFileLocationInputDataValidationTest extends Specification {

    def commandLineFileLocationInputDataValidation = new CommandLineFileLocationInputDataValidation()

    def "CommandLineFileLocationInputDataValidation - should throw an exception when file location null or empty"() {
        setup:
        def data = ["main.class", fileLocation] as String[]

        when:
        commandLineFileLocationInputDataValidation.validate(data)

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "first command-line argument, file location is missing."

        where:
        fileLocation | _
        null         | _
        ""           | _
    }

    def "CommandLineFileLocationInputDataValidation - should throw an exception when file doesn't exists"() {
        setup:
        def file = File.createTempFile("test", ".tmp")
        file.deleteOnExit()

        def fileLocation = file.path

        file.delete()

        def data = ["main.class", fileLocation] as String[]

        when:
        commandLineFileLocationInputDataValidation.validate(data)

        then:
        thrown(RuntimeException)
    }

    def "CommandLineFileLocationInputDataValidation - should throw an exception when when given path doesn't contains a valid file"() {
        setup:
        def file = File.createTempDir()
        file.deleteOnExit()

        def fileLocation = file.path

        def data = ["main.class", fileLocation] as String[]

        when:
        commandLineFileLocationInputDataValidation.validate(data)

        then:
        thrown(RuntimeException)
    }

    def "CommandLineFileLocationInputDataValidation - should not throw any exception when file exists"() {
        setup:
        def file = File.createTempFile("test", ".tmp")
        file.deleteOnExit()

        def fileLocation = file.path

        def data = ["main.class", fileLocation] as String[]

        when:
        commandLineFileLocationInputDataValidation.validate(data)

        then:
        notThrown()
    }
}
