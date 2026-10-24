package com.claims.stoploss_monitor.members.repository;

import com.claims.stoploss_monitor.members.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository
        extends JpaRepository<Member, UUID> {

    Optional<Member> findByMemberId(
            String memberId
    );


}