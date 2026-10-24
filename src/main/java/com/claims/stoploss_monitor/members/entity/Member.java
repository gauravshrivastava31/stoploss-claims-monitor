package com.claims.stoploss_monitor.members.entity;

import com.claims.stoploss_monitor.groups.entity.Group;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "group_id", nullable = false)
    private String groupId;

    @Column(name = "member_id", nullable = false, unique = true)
    private String memberId;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String relationship;
}