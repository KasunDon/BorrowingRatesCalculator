package com.zopa.borrowingratescalculator.domain.validator;

import java.util.List;

public class CompositeInputDataValidator implements InputDataValidator {

    private final List<InputDataValidator> inputDataValidators;

    public CompositeInputDataValidator(
        List<InputDataValidator> inputDataValidators
    ) {
        this.inputDataValidators = inputDataValidators;
    }

    public void validate(
        String[] data
    ) {
        inputDataValidators
            .forEach(inputDataValidator -> inputDataValidator.validate(data));
    }
}
