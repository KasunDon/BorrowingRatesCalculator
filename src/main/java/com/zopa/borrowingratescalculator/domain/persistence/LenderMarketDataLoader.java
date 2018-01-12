package com.zopa.borrowingratescalculator.domain.persistence;

import com.zopa.borrowingratescalculator.domain.entity.Lender;

import java.util.List;

public interface LenderMarketDataLoader {

    List<Lender> load();
}
