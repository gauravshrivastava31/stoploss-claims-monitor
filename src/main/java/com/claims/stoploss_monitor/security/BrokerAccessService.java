package com.claims.stoploss_monitor.security;

import org.springframework.stereotype.Service;

@Service
public class BrokerAccessService {

    public boolean canAccessGroup(
            String username,
            String groupId
    ) {

        if ("analyst".equals(username)) {
            return true;
        }

        if ("brokerA".equals(username)
                && "GA".equalsIgnoreCase(groupId)) {
            return true;
        }

        if ("brokerB".equals(username)
                && "GB".equalsIgnoreCase(groupId)) {
            return true;
        }

        return false;
    }
}