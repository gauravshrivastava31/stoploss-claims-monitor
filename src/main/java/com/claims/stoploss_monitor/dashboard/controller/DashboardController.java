package com.claims.stoploss_monitor.dashboard.controller;

import com.claims.stoploss_monitor.dashboard.dto.DashboardStatsDto;
import com.claims.stoploss_monitor.groups.repository.GroupRepository;
import com.claims.stoploss_monitor.lasers.repository.LaserRepository;
import com.claims.stoploss_monitor.members.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final GroupRepository groupRepository;

    private final MemberRepository memberRepository;

    private final LaserRepository laserRepository;

    @GetMapping("/stats")
    public DashboardStatsDto getStats() {

        return DashboardStatsDto.builder()
                .groups(
                        groupRepository.count()
                )
                .members(
                        memberRepository.count()
                )
                .lasers(
                        laserRepository.count()
                )
                .alerts(0)
                .build();
    }
}