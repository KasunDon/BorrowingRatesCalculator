package com.zopa.borrowingratescalculator.domain.mapper

import spock.lang.Specification

class LenderMarketDataMapperTest extends Specification {

    def lenderMarketDataMapper = new LenderMarketDataMapper()

    def "LenderMarketDataMapper - should be able to make String[] to Lender object"() {
        setup:
        def data = ["JK", "0.1234", "1000.1234"] as String[]

        when:
        def lender = lenderMarketDataMapper.map(data)

        then:
        lender.name == "JK"
        lender.interestRate.rate == 0.1234
        lender.balance.amount == 1000.12
    }

    def "LenderMarketDataMapper - incorrect number of elements in String[] should throw an exception"() {
        setup:
        def data = ["JK", "0.1234"] as String[]

        when:
        lenderMarketDataMapper.map(data)

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "mapping source data doesn't contain required number of elements."
    }
}
