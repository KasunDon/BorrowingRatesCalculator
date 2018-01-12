package com.zopa.borrowingratescalculator.domain.validator.cli;

import com.zopa.borrowingratescalculator.domain.validator.InputDataValidator;
import org.apache.commons.lang.math.NumberUtils;


public class CommandLineLoanAmountInputDataValidation implements InputDataValidator {

    public void validate(
        String[] data
    ) {
        if (data.length < 3 ||
            data[2] == null ||
            data[2].isEmpty()) {
            throw new IllegalArgumentException("second command-line argument, loan amount is missing.");
        }

        if (!NumberUtils.isNumber(data[2])) {
            throw new NumberFormatException("loan amount is not a number.");
        }
    }

}
