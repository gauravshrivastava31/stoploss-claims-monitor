package com.claims.stoploss_monitor.alerts.service;

import com.claims.stoploss_monitor.alerts.dto.AlertDto;
import com.claims.stoploss_monitor.claims.repository.ClaimVersionRepository;
import com.claims.stoploss_monitor.lasers.entity.Laser;
import com.claims.stoploss_monitor.lasers.repository.LaserRepository;
import com.claims.stoploss_monitor.members.entity.Member;
import com.claims.stoploss_monitor.members.repository.MemberRepository;
import com.claims.stoploss_monitor.policies.repository.PolicyYearRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.RoundingMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlertGenerationServiceImpl
        implements AlertGenerationService {

    private final ClaimVersionRepository claimVersionRepository;
    private final MemberRepository memberRepository;
    private final PolicyYearRepository policyYearRepository;
    private final LaserRepository laserRepository;

    @Override
    public List<AlertDto> evaluateAlerts() {

        List<AlertDto> alerts = new ArrayList<>();

        List<Object[]> spendData =
                claimVersionRepository.calculateMemberSpend();

        for (Object[] row : spendData) {

            String memberId = (String) row[0];

            BigDecimal spend = (BigDecimal) row[1];

            Member member =
                    memberRepository
                            .findByMemberId(memberId)
                            .orElse(null);

            if (member == null) {
                continue;
            }

            BigDecimal deductible;

            Optional<Laser> laser =
                    laserRepository.findByMemberId(memberId);

            if (laser.isPresent()) {

                deductible =
                        laser.get()
                                .getSpecificDeductible();

            } else {

                deductible =
                        policyYearRepository
                                .findFirstByGroup_GroupId(
                                        member.getGroupId()
                                )
                                .orElseThrow()
                                .getSpecificDeductible();
            }

            BigDecimal utilization =
                    spend.multiply(
                                    BigDecimal.valueOf(100)
                            )
                            .divide(
                                    deductible,
                                    2,
                                    RoundingMode.HALF_UP
                            );

            String level = null;

            if (utilization.compareTo(
                    BigDecimal.valueOf(100))
                    >= 0) {

                level = "CRITICAL";

            } else if (utilization.compareTo(
                    BigDecimal.valueOf(90))
                    >= 0) {

                level = "HIGH";

            } else if (utilization.compareTo(
                    BigDecimal.valueOf(80))
                    >= 0) {

                level = "WARNING";
            }

            if (level != null) {

                alerts.add(
                        AlertDto.builder()
                                .memberId(memberId)
                                .groupId(
                                        member.getGroupId()
                                )
                                .spend(spend)
                                .deductible(deductible)
                                .utilizationPercent(utilization)
                                .alertLevel(level)
                                .build()
                );
            }
        }
        log.info(
                "Total alerts generated = {}",
                alerts.size()
        );
        return alerts;
    }
}