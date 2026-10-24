package com.claims.stoploss_monitor.policies.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PolicyCsvRecord {

    @CsvBindByName(column = "group_id")
    private String groupId;

    @CsvBindByName(column = "group_name")
    private String groupName;

    @CsvBindByName(column = "policy_start")
    private String policyStart;

    @CsvBindByName(column = "policy_end")
    private String policyEnd;

    @CsvBindByName(column = "specific_deductible")
    private String specificDeductible;
}