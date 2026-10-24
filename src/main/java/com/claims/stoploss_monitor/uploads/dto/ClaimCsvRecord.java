package com.claims.stoploss_monitor.uploads.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClaimCsvRecord {

    @CsvBindByName(column = "Claim ID")
    private String claimId;

    @CsvBindByName(column = "Adj Seq")
    private Integer adjustmentSequence;

    @CsvBindByName(column = "Member ID")
    private String memberId;

    @CsvBindByName(column = "Service Date")
    private String serviceDate;

    @CsvBindByName(column = "Paid Date")
    private String paidDate;

    @CsvBindByName(column = "Paid Amount")
    private String paidAmount;

    @CsvBindByName(column = "Status")
    private String status;

    @CsvBindByName(column = "Diagnosis Code")
    private String diagnosisCode;
}