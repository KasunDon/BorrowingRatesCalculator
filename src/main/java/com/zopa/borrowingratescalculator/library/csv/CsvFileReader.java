package com.zopa.borrowingratescalculator.library.csv;

import java.io.File;

public interface CsvFileReader<T> {

    T read(File file);
}
