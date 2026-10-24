package com.claims.stoploss_monitor.members.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class MemberSummaryDto {

    private String memberId;

    private String groupId;

    private BigDecimal spend;

    private BigDecimal deductible;

    private BigDecimal utilizationPercent;

    private String alertLevel;
}