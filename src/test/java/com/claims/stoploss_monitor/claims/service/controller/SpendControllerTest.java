package com.claims.stoploss_monitor.claims.service.controller;

import com.claims.stoploss_monitor.claims.repository.ClaimVersionRepository;
import com.claims.stoploss_monitor.claims.service.SpendCalculationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpendCalculationServiceImplTest {

    @Mock
    private ClaimVersionRepository claimVersionRepository;

    @InjectMocks
    private SpendCalculationServiceImpl spendCalculationService;

    @Test
    void shouldReturnMemberSpend() {

        when(
                claimVersionRepository
                        .getTotalPaidAmountForMember("A-0077")
        ).thenReturn(
                new BigDecimal("14455.00")
        );

        BigDecimal result =
                spendCalculationService
                        .getMemberSpend("A-0077");

        assertEquals(
                new BigDecimal("14455.00"),
                result
        );
    }
}