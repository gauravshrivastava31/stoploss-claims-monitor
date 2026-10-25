package com.claims.stoploss_monitor.claims.controller;

import com.claims.stoploss_monitor.claims.service.SpendCalculationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/spend")
@RequiredArgsConstructor
@Tag(name = "Spend APIs")
@SecurityRequirement(name = "basicAuth")
public class SpendController {

    private final SpendCalculationService
            spendCalculationService;

    @GetMapping("/{memberId}")
    public BigDecimal getSpend(
            @PathVariable String memberId
    ) {

        return spendCalculationService
                .getMemberSpend(memberId);
    }
}