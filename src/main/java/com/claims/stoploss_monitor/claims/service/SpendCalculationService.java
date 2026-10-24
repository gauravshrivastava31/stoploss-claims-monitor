package com.claims.stoploss_monitor.claims.service;

import java.math.BigDecimal;

public interface SpendCalculationService {

    BigDecimal calculateMemberSpend(
            String groupId,
            String memberId
    );

}