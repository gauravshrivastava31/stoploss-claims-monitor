package com.claims.stoploss_monitor.claims.repository;

import com.claims.stoploss_monitor.claims.entity.Claim;
import com.claims.stoploss_monitor.claims.entity.ClaimVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
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

    @Query("""
       SELECT COALESCE(SUM(cv.paidAmount),0)
       FROM ClaimVersion cv
       WHERE cv.memberId = :memberId
       AND cv.isLatest = true
       """)
    BigDecimal getTotalPaidAmountForMember(
            String memberId
    );

    @Query("""
       SELECT cv.memberId,
              SUM(cv.paidAmount)
       FROM ClaimVersion cv
       WHERE cv.isLatest = true
       GROUP BY cv.memberId
       """)
    List<Object[]> calculateMemberSpend();
}