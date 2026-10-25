package com.claims.stoploss_monitor.dashboard.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DashboardStatsDto {

    private long groups;

    private long members;

    private long lasers;

    private long alerts;
}