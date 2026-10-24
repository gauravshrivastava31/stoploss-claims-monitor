package com.claims.stoploss_monitor.claims.repository;

import com.claims.stoploss_monitor.claims.entity.Claim;
import com.claims.stoploss_monitor.claims.entity.ClaimVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClaimVersionRepository
        extends JpaRepository<ClaimVersion, UUID> {

    Optional<ClaimVersion> findByClaimAndIsLatestTrue(
            Claim claim
    );

    Optional<ClaimVersion> findTopByClaimOrderByVersionNumberDesc(
            Claim claim
    );

    List<ClaimVersion> findByIsLatestTrue();
}