/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.ban.manager;

import java.util.Date;

/**
 * <p>
 * This class is a full implementation of the BanManager interface. It not only
 * implements all methods defined in the interface and all business validations
 * rules, but also utilizes Logging Wrapper component to provide useful log
 * information for product tracing and debugging. This implementation also
 * relies on Configuration Manager and Object Factory components to make the
 * underlying persistence configurable through configuration file.
 * </p>
 * <p>
 * This class is immutable and thread safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
*/
public class MockBanManager implements com.topcoder.management.ban.BanManager {

    /**
     * <p>
     * The default namesapce which this class will read configuration data from.
     * Its value should equal to the full class name of this class.
     * </p>
     *
     */
    public static final String DEFAULT_NAMESPACE = "";

    /**
     * <p>
     * Creates a new instance of this class from the default namespace. simply
     * calls this(DEFAULT_NAMESPACE).
     * </p>
     * <p>
     * <strong>Exception:</strong>
     * </p>
     * <ol>
     * <li>BanManagerConfigurationException if any error occurs when loading
     * configuration data or any configuration data is missing or invalid.</li>
     * </ol>
     *
     */
    public MockBanManager() {
        this(DEFAULT_NAMESPACE);
    }

    /**
     * <p>
     * Creates a new instance of this class from the given namesapce.
     * </p>
     * <p>
     * <strong>Exceptions:</strong>
     * </p>
     * <ol>
     * <li>IllegalArgumentException if namespace is null or empty string.</li>
     * <li>BanManagerConfigurationException if any error occurs when loading
     * configuration data or any configuration data is missing or invalid.</li>
     * </ol>
     *
     *
     * @param namespace
     *            the namespace to create this instance from.
     */
    public MockBanManager(String namespace) {
    }

    /**
     * <p>
     * This method sets a ban to a member. If the member currently has a ban in
     * effect, this ban entry overwrites(removes) the existing ban. The number
     * of days of the ban must be a positive number. Simply delegates to
     * persistence.banUser(userId, banDays).
     * </p>
     * <p>
     * <strong>Throws:</strong>
     * </p>
     * <ol>
     * <li>IllegalArgumentException if banDays is not positive.</li>
     * <li>BanManagerPersistenceException if any error occurs in the
     * persistence layer</li>
     * <li>BanManagerException if any other errors occur</li>
     * </ol>
     *
     *
     * @param userId
     *            the id of the user to ban.
     * @param banDays
     *            the number of days to ban the user.
     */
    public void banUser(long userId, int banDays) {
    }

    /**
     * <p>
     * This method eliminates the existing ban(if any) for a member currently in
     * effect. If there's no ban ineffect, it does nothing. Simply delegates to
     * persistence.removeBan(userId).
     * </p>
     * <p>
     * <strong>Throws:</strong>
     * </p>
     * <ol>
     * <li>BanManagerPersistenceException if any error occurs in the
     * persistence layer</li>
     * <li>BanManagerException if any other errors occur</li>
     * </ol>
     *
     *
     * @param userId
     *            the user for which to remove bans.
     */
    public void removeBan(long userId) {
    }

    /**
     * <p>
     * This method returns the expiration date of the current ban in effect for
     * the user. If there's no ban in effect, it returns null. Simply returns
     * persistence.getBan(userId).
     * </p>
     * <p>
     * <strong>Throws:</strong>
     * </p>
     * <ol>
     * <li>BanManagerPersistenceException if any error occurs in the
     * persistence layer</li>
     * <li>BanManagerException if any other errors occur</li>
     * </ol>
     *
     *
     * @param userId
     *            the id of the user for which to get ban expiration date.
     * @return the expiration date of the ban if user is currently banned or
     *         null if user is currently not banned
     */
    public Date getBan(long userId) {
        return null;
    }

    /**
     * <p>
     * This method returns whether the member is currently banned from
     * registering to competitions. Simply returns persistence.isBanned(userId).
     * </p>
     * <p>
     * <strong>Throws:</strong>
     * </p>
     * <ol>
     * <li>BanManagerPersistenceException if any error occurs in the
     * persistence layer</li>
     * <li>BanManagerException if any other errors occur</li>
     * </ol>
     *
     *
     * @param userId
     *            the id of the user for which to check if it's banned
     * @return true if the user is currently banned, failse otherwise.
     */
    public boolean isBanned(long userId) {
        if (userId == 3) {
            return true;
        } else {
            return false;
        }
    }
}
