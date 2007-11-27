/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.timetracker.common.TimeTrackerBean;

/**
 * <p>
 * This is a bean that represents a UserStatus, which represents a possible state that a User can have in the
 * context of the Time Tracker system.
 * </p>
 * <p>
 * Thread Safety: This class is not thread safe; Each thread is expected to work on it's own instance, or this
 * class should be used in a read-only manner for concurrent access.
 * </p>
 *
 * @author George1, enefem21
 * @version 3.2.1
 * @since 3.2.1
 */
public class UserStatus extends TimeTrackerBean {

    /** Serial version UID. */
    private static final long serialVersionUID = 143253657463454L;

    /**
     * <p>
     * This is a short String that describes the status.
     * </p>
     * <p>
     * Initial Value: null
     * </p>
     * <p>
     * Accessed In: getDescription
     * </p>
     * <p>
     * Modified In: setDescription
     * </p>
     * <p>
     * Utilized In: Not Utilized
     * </p>
     * <p>
     * Valid Values: Null (during initialization) or Not null and Not empty String objects (after setting)
     * </p>
     */
    private String description;

    /**
     * <p>
     * This indicates whether the UserStatus is active or not.
     * </p>
     */
    private boolean active;

    /**
     * <p>
     * This is the id of the company with which the UserStatus is registered with. This represents the company that
     * acknowledges the given UserStatus.
     * </p>
     */
    private long companyId;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public UserStatus() {
        // nothing to do
    }

    /**
     * <p>
     * Gets a short <code>String</code> that describes the status.
     * </p>
     *
     * @return a short String that describes the status.
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>
     * Sets a short <code>String</code> that describes the status.
     * </p>
     *
     * @param description
     *            a short String that describes the status.
     * @throws IllegalArgumentException
     *             if description is a null or empty String.
     */
    public void setDescription(String description) {
        Util.checkString(description, "description");
        if (this.description == null || !description.equals(this.description)) {
            this.description = description;
            setChanged(true);
        }
    }

    /**
     * <p>
     * This indicates whether the UserStatus is active or not.
     * </p>
     *
     * @return whether the UserStatus is active or not.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * <p>
     * This indicates whether the UserStatus is active or not.
     * </p>
     *
     * @param active
     *            whether the UserStatus is active or not.
     */
    public void setActive(boolean active) {
        if (active != this.active) {
            this.active = active;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Gets the id of the company with which the UserStatus is registered with. This represents the company that
     * acknowledges the given UserStatus.
     * </p>
     *
     * @return the id of the company with which the UserStatus is registered with.
     */
    public long getCompanyId() {
        return companyId;
    }

    /**
     * <p>
     * Sets the id of the company with which the UserStatus is registered with. This represents the company that
     * acknowledges the given UserStatus.
     * </p>
     *
     * @param companyId
     *            the id of the company with which the UserStatus is registered with.
     * @throws IllegalArgumentException
     *             if companyId is <= 0.
     */
    public void setCompanyId(long companyId) {
        if (companyId <= 0) {
            throw new IllegalArgumentException("companyId should be positive number");
        }
        if (companyId != this.companyId) {
            this.companyId = companyId;
            setChanged(true);
        }
    }
}
