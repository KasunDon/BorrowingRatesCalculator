package com.zopa.borrowingratescalculator.infrastructure.csv.opencsv

import com.opencsv.CSVReader
import com.zopa.borrowingratescalculator.domain.mapper.LenderMarketDataMapper
import com.zopa.borrowingratescalculator.domain.validator.InputDataValidator
import spock.lang.Specification

class OpenCsvLenderMarketDataFileReaderTest extends Specification {

    def inputDataValidator = Mock(InputDataValidator)
    def lenderMarketDataMapper = new LenderMarketDataMapper()
    def openCSVReaderFactory = Mock(OpenCSVReaderFactory)

    def openCsvLenderMarketDataFileReader = new OpenCsvLenderMarketDataFileReader(
        inputDataValidator,
        lenderMarketDataMapper,
        openCSVReaderFactory
    )

    def "OpenCsvLenderMarketDataFileReader - should read given csv file and return list of lenders"() {
        setup:
        def file = Mock(File)

        def csvReader = Mock(CSVReader) {
            readAll() >> [
                ["lender1", "1.2", "2455"] as String[]
            ]
        }

        when:
        def lenders = openCsvLenderMarketDataFileReader.read(file)

        then:
        1 * openCSVReaderFactory.create(_) >> csvReader
        1 * inputDataValidator.validate(_)

        lenders.size() == 1
        lenders.get(0).name == "lender1"
        lenders.get(0).interestRate.rate == 1.2
        lenders.get(0).balance.amount == 2455.00
    }

    def "OpenCsvLenderMarketDataFileReader - should return empty list of lenders when file contains no data"() {
        setup:
        def file = Mock(File)

        def csvReader = Mock(CSVReader) {
            readAll() >> [
                [] as String[]
            ]
        }

        when:
        def lenders = openCsvLenderMarketDataFileReader.read(file)

        then:
        1 * openCSVReaderFactory.create(_) >> csvReader
        1 * inputDataValidator.validate(_)

        lenders.size() == 0
    }
}
