package com.claims.stoploss_monitor.policies.entity;

import com.claims.stoploss_monitor.groups.entity.Group;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "policy_years")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PolicyYear {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "policy_start")
    private LocalDate policyStart;

    @Column(name = "policy_end")
    private LocalDate policyEnd;

    @Column(name = "specific_deductible")
    private BigDecimal specificDeductible;
}