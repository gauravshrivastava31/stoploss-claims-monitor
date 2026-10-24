package com.claims.stoploss_monitor.alerts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class AlertDto {

    private String memberId;

    private String groupId;

    private BigDecimal spend;

    private BigDecimal deductible;

    private BigDecimal utilizationPercent;

    private String alertLevel;
}