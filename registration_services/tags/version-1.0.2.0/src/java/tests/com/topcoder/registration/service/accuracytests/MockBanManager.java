/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.accuracytests;

import java.util.Date;

import com.topcoder.management.ban.BanManager;

/**
 * <p>
 * Mock implementation of BanManager.
 * </p>
 * @author mittu
 * @version 1.0
 */
public class MockBanManager implements BanManager {

    /**
     * @see com.topcoder.management.ban.BanManager#banUser(long, int)
     */
    public void banUser(long userId, int banDays) {
    }

    /**
     * @see com.topcoder.management.ban.BanManager#removeBan(long)
     */
    public void removeBan(long userId) {
    }

    /**
     * @see com.topcoder.management.ban.BanManager#getBan(long)
     */
    public Date getBan(long userId) {
        return null;
    }

    /**
     * @see com.topcoder.management.ban.BanManager#isBanned(long)
     */
    public boolean isBanned(long userId) {
        return false;
    }

}
