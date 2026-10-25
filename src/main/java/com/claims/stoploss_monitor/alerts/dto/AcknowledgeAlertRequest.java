package com.claims.stoploss_monitor.alerts.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcknowledgeAlertRequest {

    @NotBlank(
            message = "Note is required"
    )
    private String note;
}