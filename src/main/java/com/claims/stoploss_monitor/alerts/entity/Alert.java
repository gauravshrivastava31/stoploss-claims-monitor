package com.claims.stoploss_monitor.alerts.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "alerts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String memberId;

    private String groupId;

    private BigDecimal spend;

    private BigDecimal deductible;

    private BigDecimal utilizationPercent;

    private String alertLevel;

    private LocalDateTime createdAt;
}