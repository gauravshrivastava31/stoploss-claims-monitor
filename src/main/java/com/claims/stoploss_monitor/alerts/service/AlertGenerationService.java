package com.claims.stoploss_monitor.alerts.service;

import com.claims.stoploss_monitor.alerts.dto.AlertDto;

import java.util.List;

public interface AlertGenerationService {

    List<AlertDto> evaluateAlerts();
}