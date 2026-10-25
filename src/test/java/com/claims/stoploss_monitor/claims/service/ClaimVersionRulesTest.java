package com.claims.stoploss_monitor.claims.service;

import com.claims.stoploss_monitor.claims.entity.Claim;
import com.claims.stoploss_monitor.claims.entity.ClaimVersion;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ClaimVersionRulesTest {

    @Test
    void latestVersionShouldReplaceOlderVersion() {

        Claim claim =
                Claim.builder()
                        .claimNumber("100190")
                        .memberId("A-0001")
                        .groupId("GA")
                        .build();

        ClaimVersion version1 =
                ClaimVersion.builder()
                        .claim(claim)
                        .versionNumber(1)
                        .paidAmount(
                                new BigDecimal("1000")
                        )
                        .isLatest(false)
                        .build();

        ClaimVersion version2 =
                ClaimVersion.builder()
                        .claim(claim)
                        .versionNumber(2)
                        .paidAmount(
                                new BigDecimal("1500")
                        )
                        .isLatest(true)
                        .build();

        assertTrue(
                version2.getVersionNumber()
                        >
                        version1.getVersionNumber()
        );

        assertTrue(
                version2.getIsLatest()
        );

        assertFalse(
                version1.getIsLatest()
        );
    }

    @Test
    void latestVersionAmountShouldBeUsed() {

        Claim claim =
                Claim.builder()
                        .claimNumber("100190")
                        .build();

        ClaimVersion oldVersion =
                ClaimVersion.builder()
                        .claim(claim)
                        .versionNumber(1)
                        .paidAmount(
                                new BigDecimal("1000")
                        )
                        .isLatest(false)
                        .build();

        ClaimVersion latestVersion =
                ClaimVersion.builder()
                        .claim(claim)
                        .versionNumber(2)
                        .paidAmount(
                                new BigDecimal("2500")
                        )
                        .isLatest(true)
                        .build();

        assertEquals(
                new BigDecimal("2500"),
                latestVersion.getPaidAmount()
        );

        assertNotEquals(
                oldVersion.getPaidAmount(),
                latestVersion.getPaidAmount()
        );
    }
}