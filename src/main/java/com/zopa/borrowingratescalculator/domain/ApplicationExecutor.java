package com.zopa.borrowingratescalculator.domain;

import com.zopa.borrowingratescalculator.domain.compute.LoanQuotesGenerator;
import com.zopa.borrowingratescalculator.domain.entity.Money;
import com.zopa.borrowingratescalculator.domain.entity.Quote;
import com.zopa.borrowingratescalculator.domain.validator.InputDataValidator;
import com.zopa.borrowingratescalculator.library.formatter.OutputFormatter;

import java.math.BigDecimal;
import java.util.Optional;

public class ApplicationExecutor {

    private final InputDataValidator inputDataValidator;
    private final LoanQuotesGenerator loanQuotesGenerator;
    private final OutputFormatter<Quote> outputFormatter;

    private String fileLocation;
    private Money requestedLoanAmount;

    public ApplicationExecutor(
        InputDataValidator inputDataValidator,
        LoanQuotesGenerator loanQuotesGenerator,
        OutputFormatter<Quote> outputFormatter
    ) {
        this.inputDataValidator = inputDataValidator;
        this.loanQuotesGenerator = loanQuotesGenerator;
        this.outputFormatter = outputFormatter;
    }

    public boolean canExecute(
        String[] inputParameters
    ) {
        inputDataValidator
            .validate(inputParameters);

        fileLocation = inputParameters[1];
        requestedLoanAmount =
            Money.of(BigDecimal.valueOf(
                Double.valueOf(inputParameters[2])
            ));

        return true;
    }

    public void execute() {

        Optional<Quote> optionalQuote =
            loanQuotesGenerator
                .generate(
                    getRequestedLoanAmount()
                        .getAmount()
                );

        if (!optionalQuote
            .isPresent()) {
            System.out.println("Sorry!, it is not possible to provide a quote at this time.");
            return;
        }

        outputFormatter
            .format(
                optionalQuote.get()
            );
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public Money getRequestedLoanAmount() {
        return requestedLoanAmount;
    }
}
