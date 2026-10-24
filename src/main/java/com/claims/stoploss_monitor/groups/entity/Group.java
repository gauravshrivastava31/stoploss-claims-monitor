package com.claims.stoploss_monitor.groups.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "groups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group {

    @Id
    @Column(name = "group_id")
    private String groupId;

    @Column(name = "group_name")
    private String groupName;
}