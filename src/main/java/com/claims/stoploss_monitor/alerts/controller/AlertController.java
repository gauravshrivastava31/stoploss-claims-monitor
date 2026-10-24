package com.claims.stoploss_monitor.alerts.controller;

import com.claims.stoploss_monitor.alerts.dto.AlertDto;
import com.claims.stoploss_monitor.alerts.service.AlertGenerationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
@Tag(name = "Alert APIs")

public class AlertController {

    private final AlertGenerationService
            alertGenerationService;

    @GetMapping
    public List<AlertDto> getAlerts() {

        return alertGenerationService
                .evaluateAlerts();
    }
}
