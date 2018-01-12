package com.zopa.borrowingratescalculator.domain.formatter;

import com.zopa.borrowingratescalculator.domain.entity.Quote;
import com.zopa.borrowingratescalculator.library.formatter.OutputFormatter;

public class CommandLineOutputFormatter implements OutputFormatter<Quote> {

    public void format(
        Quote quote
    ) {
        System.out.printf("Requested amount: £%s\n", quote.getLoanAmount().getAmount());
        System.out.printf("Rate: %s%%\n", quote.getInterestRate().getRate());
        System.out.printf("Monthly repayment: £%s\n", quote.getMonthlyRepayment().getAmount());
        System.out.printf("Total repayment: £%s\n", quote.getTotalRepayment().getAmount());
    }

}
