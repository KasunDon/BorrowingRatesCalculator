package com.zopa.borrowingratescalculator;

import com.zopa.borrowingratescalculator.domain.ApplicationExecutor;
import com.zopa.borrowingratescalculator.domain.InterestRatesFinder;
import com.zopa.borrowingratescalculator.domain.persistence.LenderMarketDataLoader;
import com.zopa.borrowingratescalculator.infrastructure.csv.LenderMarketDataLoaderFactory;
import com.zopa.borrowingratescalculator.infrastructure.spring.ClassPathXmlApplicationContextFactory;
import org.springframework.context.ApplicationContext;

public class BorrowingRatesCalculatorApp {

    public static void main(String[] args) {
        ApplicationContext applicationContext =
            ClassPathXmlApplicationContextFactory.getOrCreate();

        ApplicationExecutor applicationExecutor =
            applicationContext.getBean(ApplicationExecutor.class);

        if (applicationExecutor.canExecute(args)) {

            LenderMarketDataLoader lenderMarketDataLoader =
                applicationContext
                    .getBean(LenderMarketDataLoaderFactory.class)
                    .create(applicationExecutor.getFileLocation());

            applicationContext
                .getBean(InterestRatesFinder.class)
                .addLenders(
                    lenderMarketDataLoader.load()
                )
                .addLoanAmount(
                    applicationExecutor
                        .getRequestedLoanAmount()
                );

            applicationExecutor.execute();
        }
    }
}
