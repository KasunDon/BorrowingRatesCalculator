package com.zopa.borrowingratescalculator.infrastructure.csv;

import com.zopa.borrowingratescalculator.domain.entity.Lender;
import com.zopa.borrowingratescalculator.domain.persistence.LenderMarketDataLoader;
import com.zopa.borrowingratescalculator.library.csv.CsvFileReader;

import java.io.File;
import java.util.List;

public class CsvLenderMarketDataLoader implements LenderMarketDataLoader {

    private final String fileLocation;
    private final CsvFileReader<List<Lender>> csvFileReader;

    public CsvLenderMarketDataLoader(
        String fileLocation,
        CsvFileReader csvFileReader
    ) {
        this.fileLocation = fileLocation;
        this.csvFileReader = csvFileReader;
    }

    public List<Lender> load() {
        return csvFileReader
            .read(
                new File(fileLocation)
            );
    }

}
