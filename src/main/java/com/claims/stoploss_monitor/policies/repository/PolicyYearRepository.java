package com.claims.stoploss_monitor.policies.repository;

import com.claims.stoploss_monitor.policies.entity.PolicyYear;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PolicyYearRepository
        extends JpaRepository<PolicyYear, UUID> {
}