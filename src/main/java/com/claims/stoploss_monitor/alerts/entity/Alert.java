package com.claims.stoploss_monitor.alerts.entity;

import jakarta.persistence.*;
import lombok.*;

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

    private String groupId;

    private String memberId;

    private String alertType;

    private String message;

    private boolean resolved;

    private LocalDateTime createdAt;
}