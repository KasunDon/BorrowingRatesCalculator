## BorrowingRatesCalculatorApp - Calculates competitive lending quotes from pool of lenders.
This is a simple Java program to calculate best matching lender quotes using lender market data.

### Usage
Basic usage of this app is to run following command after packaging `Fat-JAR`.

```
$ ./run.sh ~/Downloads/lenderMarketData.csv 1000
Requested amount: £1000.00
Rate: 0.1%
Monthly repayment: £28.48
Total repayment: £1025.14
```


### Requirements
This project requires following software versions or higher in order to compile, package and execute the JAR.

```
JDK 8 or higher
maven2 or higher
```

### Tests
If interested to run tests in this project, you could easily execute following commands.

##### Unit Test
```
mvn clean test
```

##### Integration Test
```
mvn clean verify
```

#### Packaging
Fat JAR can be easily build by running following `maven` command.

```
mvn clean package
```