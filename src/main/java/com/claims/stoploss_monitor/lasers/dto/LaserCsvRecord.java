package com.claims.stoploss_monitor.lasers.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LaserCsvRecord {

    @CsvBindByName(column = "group_id")
    private String groupId;

    @CsvBindByName(column = "member_id")
    private String memberId;

    @CsvBindByName(column = "policy_start")
    private String policyStart;

    @CsvBindByName(column = "specific_deductible")
    private String specificDeductible;
}