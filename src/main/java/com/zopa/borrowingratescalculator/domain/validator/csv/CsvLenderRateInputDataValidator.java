package com.zopa.borrowingratescalculator.domain.validator.csv;

import com.zopa.borrowingratescalculator.domain.validator.InputDataValidator;
import org.apache.commons.lang.math.NumberUtils;

public class CsvLenderRateInputDataValidator implements InputDataValidator {

    public void validate(
        String[] data
    ) {
        if (data.length < 2 ||
            data[1] == null ||
            data[1].isEmpty()) {
            throw new IllegalArgumentException("interest rate is missing.");
        }

        if (!NumberUtils.isNumber(data[1])) {
            throw new NumberFormatException("interest rate is not a number.");
        }
    }
}
