package com.claims.stoploss_monitor.claims.repository;

import com.claims.stoploss_monitor.claims.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClaimRepository
        extends JpaRepository<Claim, UUID> {

    Optional<Claim> findByClaimNumber(String claimNumber);
}