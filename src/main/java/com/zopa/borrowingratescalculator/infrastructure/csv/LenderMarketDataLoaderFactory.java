package com.zopa.borrowingratescalculator.infrastructure.csv;

import com.zopa.borrowingratescalculator.domain.persistence.LenderMarketDataLoader;
import com.zopa.borrowingratescalculator.library.csv.CsvFileReader;

public class LenderMarketDataLoaderFactory {

    private final CsvFileReader csvFileReader;

    public LenderMarketDataLoaderFactory(
        CsvFileReader csvFileReader
    ) {
        this.csvFileReader = csvFileReader;
    }

    public LenderMarketDataLoader create(
        String fileLocation
    ) {
        return new CsvLenderMarketDataLoader(
            fileLocation,
            csvFileReader
        );
    }
}
