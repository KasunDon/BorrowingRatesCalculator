package com.zopa.borrowingratescalculator.domain.compute

import com.zopa.borrowingratescalculator.domain.entity.InterestRate
import com.zopa.borrowingratescalculator.domain.entity.Lender
import com.zopa.borrowingratescalculator.domain.entity.Money
import com.zopa.borrowingratescalculator.domain.exception.ApplicableLenderNotFoundException
import spock.lang.Specification

class InterestRateComputeRuleExecutorTest extends Specification {

    def lenders = [
        new Lender("lender5", InterestRate.of(BigDecimal.valueOf(5)), Money.of(BigDecimal.valueOf(4000))),
        new Lender("lender4", InterestRate.of(BigDecimal.valueOf(4)), Money.of(BigDecimal.valueOf(4000))),
        new Lender("lender1", InterestRate.of(BigDecimal.valueOf(1)), Money.of(BigDecimal.valueOf(1000))),
        new Lender("lender2", InterestRate.of(BigDecimal.valueOf(2)), Money.of(BigDecimal.valueOf(2000))),
        new Lender("lender3", InterestRate.of(BigDecimal.valueOf(3)), Money.of(BigDecimal.valueOf(3000)))
    ]

    def interestComputeRule1 = Mock(InterestRateComputeRule)
    def interestComputeRule2 = Mock(InterestRateComputeRule)

    def interestComputeRules = [
        interestComputeRule1,
        interestComputeRule2
    ]

    def interestRateComputeRuleExecutor = new InterestRateComputeRuleExecutor(interestComputeRules)

    def "InterestRateComputeRuleExecutor - should iterate through available rules and collect calculated interest rates"() {
        setup:
        def loanAmount = Money.of(1000)

        when:
        def interestRate = interestRateComputeRuleExecutor.compute(loanAmount, lenders)

        then:
        1 * interestComputeRule1.compute(_, _) >> Optional.empty()
        1 * interestComputeRule2.compute(_, _) >> Optional.of(InterestRate.of(1.11))

        interestRate instanceof Optional
    }

    def "InterestRateComputeRuleExecutor - return empty optional when caught a non applicable lender exception otherwise throw out"() {
        setup:
        def loanAmount = Money.of(1000)

        when:
        def interestRate = interestRateComputeRuleExecutor.compute(loanAmount, lenders)

        then:
        1 * interestComputeRule1.compute(_, _) >> {
            throw new ApplicableLenderNotFoundException()
        }

        interestRate instanceof Optional
        interestRate.isPresent() == false

        when:
        interestRateComputeRuleExecutor.compute(loanAmount, lenders)

        then:
        1 * interestComputeRule1.compute(_, _) >> Optional.of(InterestRate.of(1.10))
        1 * interestComputeRule2.compute(_, _) >> {
            throw new Exception()
        }

        thrown(Exception)
    }

    def "InterestRateComputeRuleExecutor - should round up interest rates to 1 decimal point and pick lowest interest rate"() {
        setup:
        def loanAmount = Money.of(1000)

        when:
        def interestRate = interestRateComputeRuleExecutor.compute(loanAmount, lenders)

        then:
        1 * interestComputeRule1.compute(_, _) >> Optional.of(InterestRate.of(1.91))
        1 * interestComputeRule2.compute(_, _) >> Optional.of(InterestRate.of(1.11))

        interestRate.get().rate == 1.1
    }

    def "InterestRateComputeRuleExecutor - should return empty optional interest rate when there are no interest rates"() {
        setup:
        def loanAmount = Money.of(1000)

        when:
        def interestRate = interestRateComputeRuleExecutor.compute(loanAmount, lenders)

        then:
        1 * interestComputeRule1.compute(_, _) >> Optional.empty()
        1 * interestComputeRule2.compute(_, _) >> Optional.empty()

        interestRate instanceof Optional

        interestRate.isPresent() == false
    }
}
