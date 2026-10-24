package com.claims.stoploss_monitor.members.controller;

import com.claims.stoploss_monitor.claims.service.SpendCalculationService;
import com.claims.stoploss_monitor.lasers.entity.Laser;
import com.claims.stoploss_monitor.lasers.repository.LaserRepository;
import com.claims.stoploss_monitor.members.dto.MemberSummaryDto;
import com.claims.stoploss_monitor.members.entity.Member;
import com.claims.stoploss_monitor.members.repository.MemberRepository;
import com.claims.stoploss_monitor.policies.entity.PolicyYear;
import com.claims.stoploss_monitor.policies.repository.PolicyYearRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final SpendCalculationService spendCalculationService;

    private final MemberRepository memberRepository;

    private final LaserRepository laserRepository;

    private final PolicyYearRepository policyYearRepository;

    @GetMapping("/{memberId}/summary")
    public MemberSummaryDto getSummary(
            @PathVariable String memberId
    ) {

        Member member =
                memberRepository
                        .findByMemberId(memberId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Member not found: "
                                                + memberId
                                )
                        );

        BigDecimal spend =
                spendCalculationService
                        .getMemberSpend(memberId);

        BigDecimal deductible;

        Optional<Laser> laser =
                laserRepository.findByMemberId(memberId);

        if (laser.isPresent()) {

            deductible =
                    laser.get()
                            .getSpecificDeductible();

        } else {

            PolicyYear policyYear =
                    policyYearRepository
                            .findFirstByGroup_GroupId(
                                    member.getGroupId()
                            )
                            .orElseThrow();

            deductible =
                    policyYear
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

        String alertLevel = null;

        if (utilization.compareTo(
                BigDecimal.valueOf(100)
        ) >= 0) {

            alertLevel = "CRITICAL";

        } else if (utilization.compareTo(
                BigDecimal.valueOf(90)
        ) >= 0) {

            alertLevel = "HIGH";

        } else if (utilization.compareTo(
                BigDecimal.valueOf(80)
        ) >= 0) {

            alertLevel = "WARNING";
        }

        return MemberSummaryDto
                .builder()
                .memberId(memberId)
                .groupId(member.getGroupId())
                .spend(spend)
                .deductible(deductible)
                .utilizationPercent(utilization)
                .alertLevel(alertLevel)
                .build();
    }
}