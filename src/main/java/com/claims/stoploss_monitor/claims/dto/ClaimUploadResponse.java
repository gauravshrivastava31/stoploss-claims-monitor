package com.claims.stoploss_monitor.claims.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ClaimUploadResponse {

    private String fileName;

    private int processedRows;

    private int failedRows;

    private List<String> errors;
}