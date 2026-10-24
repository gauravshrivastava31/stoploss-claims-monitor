package com.claims.stoploss_monitor.alerts.service;

import com.claims.stoploss_monitor.alerts.dto.AlertDto;
import com.claims.stoploss_monitor.claims.repository.ClaimVersionRepository;
import com.claims.stoploss_monitor.groups.entity.Group;
import com.claims.stoploss_monitor.lasers.repository.LaserRepository;
import com.claims.stoploss_monitor.members.entity.Member;
import com.claims.stoploss_monitor.members.repository.MemberRepository;
import com.claims.stoploss_monitor.policies.entity.PolicyYear;
import com.claims.stoploss_monitor.policies.repository.PolicyYearRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlertGenerationServiceImplTest {

    @Mock
    private ClaimVersionRepository claimVersionRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PolicyYearRepository policyYearRepository;

    @Mock
    private LaserRepository laserRepository;

    @InjectMocks
    private AlertGenerationServiceImpl alertGenerationService;

    @Test
    void shouldGenerateHighAlertWhenUtilizationIsNinetyPercent() {

        Object[] spendRow = new Object[]{
                "A-0077",
                new BigDecimal("45000")
        };

        List<Object[]> spendData = new ArrayList<>();
        spendData.add(spendRow);

        when(
                claimVersionRepository.calculateMemberSpend()
        ).thenReturn(spendData);

        Member member =
                Member.builder()
                        .id(UUID.randomUUID())
                        .memberId("A-0077")
                        .groupId("GA")
                        .build();

        when(
                memberRepository.findByMemberId(
                        "A-0077"
                )
        ).thenReturn(
                Optional.of(member)
        );

        when(
                laserRepository.findByMemberId(
                        "A-0077"
                )
        ).thenReturn(
                Optional.empty()
        );

        Group group =
                Group.builder()
                        .groupId("GA")
                        .groupName("Group A")
                        .build();

        PolicyYear policyYear =
                PolicyYear.builder()
                        .id(UUID.randomUUID())
                        .group(group)
                        .policyStart(LocalDate.now())
                        .policyEnd(LocalDate.now().plusYears(1))
                        .specificDeductible(
                                new BigDecimal("50000")
                        )
                        .build();

        when(
                policyYearRepository
                        .findFirstByGroup_GroupId(
                                "GA"
                        )
        ).thenReturn(
                Optional.of(policyYear)
        );

        List<AlertDto> result =
                alertGenerationService
                        .evaluateAlerts();

        assertEquals(
                1,
                result.size()
        );

        assertEquals(
                "HIGH",
                result.get(0).getAlertLevel()
        );
    }
}