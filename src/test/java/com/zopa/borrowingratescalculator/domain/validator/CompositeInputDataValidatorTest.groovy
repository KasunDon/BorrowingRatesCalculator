package com.zopa.borrowingratescalculator.domain.validator

import spock.lang.Specification

class CompositeInputDataValidatorTest extends Specification {

    def "CompositeInputDataValidator - should iterate through given validators"() {
        setup:
        def validator1 = Mock(InputDataValidator)
        def validator2 = Mock(InputDataValidator)

        def compositeInputDataValidator = new CompositeInputDataValidator([
            validator1,
            validator2
        ])

        def data = [] as String[]

        when:
        compositeInputDataValidator.validate(data)

        then:
        1 * validator1.validate(_)
        1 * validator2.validate(_)
    }

    def "CompositeInputDataValidator - should pause iterations when exception is thrown out of validators"() {
        setup:
        def validator1 = Mock(InputDataValidator)
        def validator2 = Mock(InputDataValidator)

        def compositeInputDataValidator = new CompositeInputDataValidator([
            validator1,
            validator2
        ])

        def data = [] as String[]

        when:
        compositeInputDataValidator.validate(data)

        then:
        1 * validator1.validate(_) >> {
            throw new IllegalArgumentException()
        }

        thrown(IllegalArgumentException)

        0 * validator2.validate(_)
    }

}
