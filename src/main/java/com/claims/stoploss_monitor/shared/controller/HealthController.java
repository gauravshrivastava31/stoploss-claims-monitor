package com.claims.stoploss_monitor.shared.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name = "basicAuth")
public class HealthController {

    @GetMapping("/health")
    public String health() {
        return "Application Running";
    }
}