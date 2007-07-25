/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.failuretests.impl;

import java.util.Date;

import com.topcoder.management.ban.BanManager;

/**
 * The mock class for <code>BanManager</code>.
 *
 * @author liulike
 * @version 1.0
 */
public class MockBanManager implements BanManager {

    /**
     * The default ctor.
     */
    public MockBanManager() {

    }

    /**
     * <p>
     * This method sets a ban to a member. If the member currently has a ban in effect, this ban entry
     * overwrites(removes) the existing ban. The number of days of the ban must be a positive number.
     * </p>
     *
     * @param userId the id of the user to ban.
     * @param banDays the number of days to ban the user.
     */

    public void banUser(long userId, int banDays) {
    }

    /**
     * <p>
     * This method eliminates the existing ban(if any) for a member currently in effect. If there's no ban in
     * effect, it does nothing.
     * </p>
     *
     * @param userId the user for which to remove bans.
     */
    public void removeBan(long userId) {
    }

    /**
     * <p>
     * This method returns the expiration date of the current ban in effect for the user. If there's no ban in
     * effect, it returns null.
     * </p>
     *
     * @param userId the id of the user for which to get ban expiration date.
     * @return the expiration date of the ban if user is currently banned or null if user is currently not
     *         banned
     */
    public Date getBan(long userId) {
        return null;
    }

    /**
     * <p>
     * This method returns whether the member is currently banned from registering to competitions.
     * </p>
     *
     * @param userId the id of the user for which to check if it's banned
     * @return true if the user is currently banned, false otherwise.
     */
    public boolean isBanned(long userId) {
        return false;
    }

}
