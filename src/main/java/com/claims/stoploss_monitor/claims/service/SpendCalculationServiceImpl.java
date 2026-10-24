package com.claims.stoploss_monitor.claims.service;

import com.claims.stoploss_monitor.claims.repository.ClaimVersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class SpendCalculationServiceImpl
        implements SpendCalculationService {

    private final ClaimVersionRepository claimVersionRepository;

    @Override
    public BigDecimal calculateMemberSpend(
            String groupId,
            String memberId
    ) {

        // to be implemented after CSV import

        return BigDecimal.ZERO;
    }
}