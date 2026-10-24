package com.claims.stoploss_monitor.alerts.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class MemberSpendSummary {

    private String memberId;

    private BigDecimal spend;

    private BigDecimal deductible;

    private BigDecimal utilizationPercent;

    private Boolean laserApplied;
}
