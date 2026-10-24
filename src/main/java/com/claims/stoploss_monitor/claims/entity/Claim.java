package com.claims.stoploss_monitor.claims.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "claims")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "group_id")
    private String groupId;

    @Column(name = "member_id")
    private String memberId;

    @Column(
            name = "claim_number",
            nullable = false,
            unique = true
    )
    private String claimNumber;
}