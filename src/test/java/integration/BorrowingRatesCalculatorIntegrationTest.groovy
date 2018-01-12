package integration

import com.zopa.borrowingratescalculator.domain.ApplicationExecutor
import com.zopa.borrowingratescalculator.domain.InterestRatesFinder
import com.zopa.borrowingratescalculator.domain.persistence.LenderMarketDataLoader
import com.zopa.borrowingratescalculator.infrastructure.csv.LenderMarketDataLoaderFactory
import integration.helper.ClassPathXmlApplicationContextFactory
import spock.lang.Specification

import java.nio.file.Paths

class BorrowingRatesCalculatorIntegrationTest extends Specification {

    def applicationExecutor
    def applicationContext

    def setup() {
        System.setProperty("spring.profiles.active", "test")

        applicationContext = ClassPathXmlApplicationContextFactory.create()

        applicationExecutor = applicationContext.getBean(ApplicationExecutor.class)
    }


    def "BorrowingRatesCalculator - try find best quote"() {
        setup:

        def byteArrayOutputStream = new ByteArrayOutputStream()
        def printStream = new PrintStream(byteArrayOutputStream)
        System.out = printStream

        URL resource =
            getClass()
                .getResource("/data/lenderMarketData.csv")

        def lenderMarketDataFile =
            Paths.get(resource.toURI())
                .toFile()

        def fileLocation = lenderMarketDataFile.path
        def loanAmount = "10000"

        def inputParameters = [this.class.toString(), fileLocation, loanAmount] as String[]

        when:
        def canHandle = applicationExecutor.canExecute(inputParameters)

        then:

        canHandle == true

        applicationExecutor.fileLocation == fileLocation
        applicationExecutor.requestedLoanAmount.amount == 10000.00

        when:

        LenderMarketDataLoader lenderMarketDataLoader =
            applicationContext
                .getBean(LenderMarketDataLoaderFactory.class)
                .create(applicationExecutor.getFileLocation())

        applicationContext
            .getBean(InterestRatesFinder.class)
            .addLenders(
            lenderMarketDataLoader.load()
        )
            .addLoanAmount(
            applicationExecutor
                .getRequestedLoanAmount()
        )

        applicationExecutor.execute()

        then:
        byteArrayOutputStream.toString() ==
        "Requested amount: £10000.00\n" +
        "Rate: 0.1%\n" +
        "Monthly repayment: £287.95\n" +
        "Total repayment: £10366.37\n"

        when:

        def byteArrayOutputStream2 = new ByteArrayOutputStream()
        def printStream2 = new PrintStream(byteArrayOutputStream2)
        System.out = printStream2

        def inputParameters2 = [this.class.toString(), fileLocation, "15000"] as String[]
        applicationExecutor.canExecute(inputParameters2)
        LenderMarketDataLoader lenderMarketDataLoader2 =
            applicationContext
                .getBean(LenderMarketDataLoaderFactory.class)
                .create(applicationExecutor.getFileLocation())

        applicationContext
            .getBean(InterestRatesFinder.class)
            .addLenders(
            lenderMarketDataLoader2.load()
        )
            .addLoanAmount(
            applicationExecutor
                .getRequestedLoanAmount()
        )

        applicationExecutor.execute()

        then:
        byteArrayOutputStream2.toString() == "Sorry!, it is not possible to provide a quote at this time.\n"
    }
}
