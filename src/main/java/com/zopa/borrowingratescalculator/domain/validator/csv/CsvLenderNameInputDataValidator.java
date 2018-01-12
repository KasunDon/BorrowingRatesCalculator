package com.zopa.borrowingratescalculator.domain.validator.csv;

import com.zopa.borrowingratescalculator.domain.validator.InputDataValidator;

public class CsvLenderNameInputDataValidator implements InputDataValidator {

    public void validate(
        String[] data
    ) {
        if (data.length < 1 ||
            data[0] == null ||
            data[0].isEmpty()) {
            throw new IllegalArgumentException("lender name is missing.");
        }
    }
}
