/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.user;

import com.topcoder.timetracker.common.TimeTrackerBean;

/**
 * <p>
 * This class holds the information of a user. No parameter checking is provided in this class,
 * since the class acts as a mock class for Time Tracker Report component. All the information is
 * reading from the database, so it is assumed to be valid.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.1
 */
public class User extends TimeTrackerBean {
    /**
     * <p>
     * This is a number that uniquely identifies the company that this user is associated with. This
     * is used to tie into the Time Tracker Company component.
     * </p>
     * <p>
     * Initial Value: 0 (This indicates an uninitialized value)
     * </p>
     * <p>
     * Accessed In: getCompanyId
     * </p>
     * <p>
     * Modified In: setCompanyId
     * </p>
     * <p>
     * Utilized In: Not Utilized
     * </p>
     * <p>
     * Valid Values: Ids that are >= 0.
     * </p>
     */
    private long companyId;

    /**
     * <p>
     * This is the current status of the user account. A user's status may be ACTIVE, INACTIVE, or
     * LOCKED.
     * </p>
     * <p>
     * Initial Value: Null
     * </p>
     * <p>
     * Accessed In: getStatus
     * </p>
     * <p>
     * Modified In: setStatus
     * </p>
     * <p>
     * Utilized In: Not Utilized
     * </p>
     * <p>
     * Valid Values: Null, or Status Objects
     * </p>
     */
    private Status status;

    /**
     * <p>
     * This is the username that is used by the user. It is the common name which is used to refer
     * to an individual user.
     * </p>
     * <p>
     * Initial Value: Null
     * </p>
     * <p>
     * Accessed In: getUsername
     * </p>
     * <p>
     * Modified In: setUsername
     * </p>
     * <p>
     * Utilized In: Not Utilized
     * </p>
     * <p>
     * Valid Values: Null values, or Strings that are not empty
     * </p>
     */
    private String username;

    /**
     * <p>
     * This is the user's password. It is known only to the user, and is used to authenticate a
     * user.
     * </p>
     * <p>
     * Initial Value: Null
     * </p>
     * <p>
     * Accessed In: getPassword
     * </p>
     * <p>
     * Modified In: setPassword
     * </p>
     * <p>
     * Utilized In: Not Utilized
     * </p>
     * <p>
     * Valid Values: Nulls, or Strings that are not empty
     * </p>
     */
    private String password;

    /**
     * <p>
     * Default Constructor.
     * </p>
     */
    public User() {
        // empty
    }

    /**
     * <p>
     * Retrieves a number that uniquely identifies the company that this user is associated with.
     * </p>
     *
     * @return a number that uniquely identifies the company that this user is associated with.
     */
    public long getCompanyId() {
        return this.companyId;
    }

    /**
     * <p>
     * Sets a number that uniquely identifies the company that this user is associated with.
     * </p>
     *
     * @param companyId a number that uniquely identifies the company that this user is associated
     *        with.
     */
    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    /**
     * <p>
     * Retrieves the current status of the user account. A user's status may be ACTIVE, INACTIVE, or
     * LOCKED.
     * </p>
     *
     * @return the current status of the user account.
     */
    public Status getStatus() {
        return this.status;
    }

    /**
     * <p>
     * Gets the current status of the user account. A user's status may be ACTIVE, INACTIVE, or
     * LOCKED.
     * </p>
     *
     * @param status the current status of the user account.
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * <p>
     * Retrieves the username of the user.
     * </p>
     *
     * @return the username that is used by the user.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * <p>
     * Sets the username of the user.
     * </p>
     *
     * @param username the username of the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * <p>
     * Retrieves the user's password.
     * </p>
     *
     * @return the user's password.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * <p>
     * Sets the user's password.
     * </p>
     *
     * @param password the description of a project.
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
