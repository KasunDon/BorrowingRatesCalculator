package com.zopa.borrowingratescalculator.infrastructure.csv

import com.zopa.borrowingratescalculator.library.csv.CsvFileReader
import spock.lang.Specification

class LenderMarketDataLoaderFactoryTest extends Specification {

    def csvFileReader = Mock(CsvFileReader)

    def lenderMarketDataLoaderFactory = new LenderMarketDataLoaderFactory(csvFileReader)

    def "LenderMarketDataLoaderFactory - should let you create instance of CsvLenderMarketDataLoader"() {
        setup:
        def fileLocation = "/some/location"

        when:
        def csvLenderMarketDataLoader = lenderMarketDataLoaderFactory.create(fileLocation)

        then:
        csvLenderMarketDataLoader instanceof CsvLenderMarketDataLoader
    }
}
