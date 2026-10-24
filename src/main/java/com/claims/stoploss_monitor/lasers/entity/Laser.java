package com.claims.stoploss_monitor.lasers.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "lasers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Laser {

    @Id
    private UUID id;

    @Column(name = "group_id")
    private String groupId;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "policy_start")
    private LocalDate policyStart;

    @Column(name = "specific_deductible")
    private BigDecimal specificDeductible;
}
