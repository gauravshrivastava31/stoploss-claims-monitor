package com.claims.stoploss_monitor.groups.repository;

import com.claims.stoploss_monitor.groups.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository
        extends JpaRepository<Group, String> {
}