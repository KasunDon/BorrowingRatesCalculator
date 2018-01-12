package com.zopa.borrowingratescalculator.infrastructure.csv.opencsv;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class OpenCSVReaderFactory {

    public CSVReader create(
        File file
    ) throws FileNotFoundException {
        Reader reader = new FileReader(file);

        return new CSVReaderBuilder(reader)
            .withSkipLines(1)
            .build();
    }
}
