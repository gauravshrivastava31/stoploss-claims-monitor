package com.claims.stoploss_monitor.alerts.repository;

import com.claims.stoploss_monitor.alerts.entity.AlertAcknowledgement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AlertAcknowledgementRepository
        extends JpaRepository<
        AlertAcknowledgement,
        UUID
        > {
}