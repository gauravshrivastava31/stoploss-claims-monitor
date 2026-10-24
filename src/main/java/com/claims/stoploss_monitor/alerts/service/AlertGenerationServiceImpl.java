package com.claims.stoploss_monitor.alerts.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AlertGenerationServiceImpl
        implements AlertGenerationService {

    @Override
    public void evaluateAlerts() {

        log.info("Starting alert evaluation");

    }
}