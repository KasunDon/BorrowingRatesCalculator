package com.zopa.borrowingratescalculator.domain.validator.cli;

import com.zopa.borrowingratescalculator.domain.validator.InputDataValidator;

import java.io.File;

public class CommandLineFileLocationInputDataValidation implements InputDataValidator {

    public void validate(
        String[] data
    ) {
        if (data.length < 2 ||
            data[1] == null ||
            data[1].isEmpty()) {
            throw new IllegalArgumentException("first command-line argument, file location is missing.");
        }

        String fileLocation = data[1];

        File file = new File(fileLocation);

        if (!file.exists()) {
            throw new RuntimeException(fileLocation + " : file doesn't exists.");
        }

        if (!file.isFile()) {
            throw new RuntimeException("Given path doesn't contain a valid file. [" + fileLocation + "]");
        }
    }

}
