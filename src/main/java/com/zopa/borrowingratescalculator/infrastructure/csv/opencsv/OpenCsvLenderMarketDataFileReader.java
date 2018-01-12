package com.zopa.borrowingratescalculator.infrastructure.csv.opencsv;

import com.opencsv.CSVReader;
import com.zopa.borrowingratescalculator.domain.entity.Lender;
import com.zopa.borrowingratescalculator.domain.mapper.LenderMarketDataMapper;
import com.zopa.borrowingratescalculator.domain.validator.InputDataValidator;
import com.zopa.borrowingratescalculator.library.csv.CsvFileReader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class OpenCsvLenderMarketDataFileReader implements CsvFileReader<List<Lender>> {

    private final InputDataValidator inputDataValidator;
    private final LenderMarketDataMapper lenderMarketDataMapper;
    private final OpenCSVReaderFactory openCSVReaderFactory;

    public OpenCsvLenderMarketDataFileReader(
        InputDataValidator inputDataValidator,
        LenderMarketDataMapper lenderMarketDataMapper,
        OpenCSVReaderFactory openCSVReaderFactory
    ) {
        this.inputDataValidator = inputDataValidator;
        this.lenderMarketDataMapper = lenderMarketDataMapper;
        this.openCSVReaderFactory = openCSVReaderFactory;
    }

    public List<Lender> read(
        File file
    ) {
        try {

            CSVReader csvReader = openCSVReaderFactory.create(file);

            List<String[]> lines = csvReader.readAll();

            csvReader.close();

            lines.forEach(line -> inputDataValidator.validate(line));

            return
                lines
                    .stream()
                    .filter(line -> line.length == 3)
                    .map(line -> lenderMarketDataMapper.map(line))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
