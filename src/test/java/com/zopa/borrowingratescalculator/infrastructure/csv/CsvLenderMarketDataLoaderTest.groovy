package com.zopa.borrowingratescalculator.infrastructure.csv

import com.zopa.borrowingratescalculator.domain.entity.Lender
import com.zopa.borrowingratescalculator.library.csv.CsvFileReader
import spock.lang.Specification

class CsvLenderMarketDataLoaderTest extends Specification {

    def fileLocation = "/some/location"
    def csvFileReader = Mock(CsvFileReader)

    def csvLenderMarketDataLoader = new CsvLenderMarketDataLoader(fileLocation, csvFileReader)

    def "CsvLenderMarketDataLoader - should load lenders from given file location"() {

        when:
        def lenders = csvLenderMarketDataLoader.load()

        then:
        csvFileReader.read(_) >> [Mock(Lender)]

        lenders.size() == 1

        lenders.get(0) instanceof Lender
    }

    def "CsvLenderMarketDataLoader - should return empty list of lenders when no data"() {

        when:
        def lenders = csvLenderMarketDataLoader.load()

        then:
        csvFileReader.read(_) >> []

        lenders.size() == 0
    }
}
