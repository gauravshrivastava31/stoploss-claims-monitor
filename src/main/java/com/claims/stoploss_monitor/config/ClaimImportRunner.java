package com.claims.stoploss_monitor.config;

import com.claims.stoploss_monitor.alerts.service.AlertGenerationService;
import com.claims.stoploss_monitor.lasers.service.LaserImportService;
import com.claims.stoploss_monitor.masterdata.service.MasterDataImportService;
import com.claims.stoploss_monitor.policies.service.PolicyImportService;
import com.claims.stoploss_monitor.uploads.service.ClaimImportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClaimImportRunner implements CommandLineRunner {

    private final PolicyImportService policyImportService;
    private final AlertGenerationService
            alertGenerationService;

    private final MasterDataImportService masterDataImportService;

    private final LaserImportService laserImportService;

    private final ClaimImportService claimImportService;

    @Override
    public void run(String... args) {

        policyImportService.importPolicies();

        masterDataImportService.importMembers();

        laserImportService.importLasers();

        claimImportService.importClaimFile(
                "GA_2026-01.csv"
        );
       /* alertGenerationService
                .evaluateAlerts()
                .forEach(System.out::println);*/
    }
}