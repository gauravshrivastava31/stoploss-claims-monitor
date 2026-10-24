package com.claims.stoploss_monitor.lasers.repository;

import com.claims.stoploss_monitor.lasers.entity.Laser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LaserRepository
        extends JpaRepository<Laser, UUID> {
    Optional<Laser> findByMemberId(String memberId);
}