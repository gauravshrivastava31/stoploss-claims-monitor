package com.claims.stoploss_monitor.members.entity;

import com.claims.stoploss_monitor.groups.entity.Group;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(
        name = "members",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_member",
                        columnNames = {
                                "group_id",
                                "member_id"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "member_id")
    private String memberId;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String relationship;
}
