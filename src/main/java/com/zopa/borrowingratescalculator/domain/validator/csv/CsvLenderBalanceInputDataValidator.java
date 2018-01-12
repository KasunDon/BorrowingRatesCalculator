package com.zopa.borrowingratescalculator.domain.validator.csv;

import com.zopa.borrowingratescalculator.domain.validator.InputDataValidator;
import org.apache.commons.lang.math.NumberUtils;

public class CsvLenderBalanceInputDataValidator implements InputDataValidator {

    public void validate(
        String[] data
    ) {
        if (data.length < 3 ||
            data[2] == null ||
            data[2].isEmpty()) {
            throw new IllegalArgumentException("available balance is missing.");
        }

        if (!NumberUtils.isNumber(data[2])) {
            throw new NumberFormatException("available balance is not a number.");
        }
    }
}
