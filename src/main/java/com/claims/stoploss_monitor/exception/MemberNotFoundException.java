package com.claims.stoploss_monitor.exception;

public class MemberNotFoundException
        extends RuntimeException {

    public MemberNotFoundException(
            String memberId
    ) {

        super(
                "Member not found: "
                        + memberId
        );
    }
}