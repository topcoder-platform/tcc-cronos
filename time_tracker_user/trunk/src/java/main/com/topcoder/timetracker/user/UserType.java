/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.timetracker.common.TimeTrackerBean;

/**
 * <p>
 * This is a bean class that indicates a type of user.
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
public class UserType extends TimeTrackerBean {

    /** Serial version UID. */
    private static final long serialVersionUID = -8842077219241390830L;

    /**
     * <p>
     * This is a short String that describes the type.
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
     * This indicates whether the UserType is active or not.
     * </p>
     */
    private boolean active;

    /**
     * <p>
     * This is the id of the company with which the UserType is registered with. This represents the company that
     * acknowledges the given UserType.
     * </p>
     */
    private long companyId;

    /**
     * <p>
     * Default Constructor.
     * </p>
     */
    public UserType() {
        // nothing to do
    }

    /**
     * <p>
     * Gets a short String that describes the type.
     * </p>
     *
     * @return Gets a short String that describes the type.
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>
     * Sets a short String that describes the type.
     * </p>
     *
     * @param description
     *            a short String that describes the type.
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
     * This indicates whether the <code>UserType</code> is active or not.
     * </p>
     *
     * @return whether the UserType is active or not.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * <p>
     * This indicates whether the <code>UserType</code> is active or not.
     * </p>
     *
     * @param active
     *            whether the UserType is active or not.
     */
    public void setActive(boolean active) {
        if (active != this.active) {
            this.active = active;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Gets the id of the company with which the <code>UserType</code> is registered with. This represents the
     * company that acknowledges the given <code>UserType</code>.
     * </p>
     *
     * @return the id of the company with which the UserType is registered with.
     */
    public long getCompanyId() {
        return companyId;
    }

    /**
     * <p>
     * Sets the id of the company with which the <code>UserType</code> is registered with. This represents the
     * company that acknowledges the given <code>UserType</code>.
     * </p>
     *
     * @param companyId
     *            the id of the company with which the UserType is registered with.
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
