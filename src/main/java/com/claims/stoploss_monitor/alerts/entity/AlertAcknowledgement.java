package com.claims.stoploss_monitor.alerts.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "alert_acknowledgements")
        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
                public class AlertAcknowledgement {

   @Id
    private UUID id;

    private String memberId;

    private String note;

    private String acknowledgedBy;

    private LocalDateTime acknowledgedAt;
}