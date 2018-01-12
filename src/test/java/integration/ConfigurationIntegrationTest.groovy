package integration

import com.zopa.borrowingratescalculator.domain.ApplicationExecutor
import com.zopa.borrowingratescalculator.domain.InterestRatesFinder
import integration.helper.ClassPathXmlApplicationContextFactory
import spock.lang.Specification

class ConfigurationIntegrationTest extends Specification {

    def setup() {
        System.setProperty("spring.profiles.active", "test")
    }

    def "The DI configuration loads correctly"() {
        setup:
        def applicationContext = ClassPathXmlApplicationContextFactory.create()

        when:
        applicationContext.getBean(ApplicationExecutor.class)
        applicationContext.getBean(InterestRatesFinder.class)

        then:
        noExceptionThrown()
    }
}
