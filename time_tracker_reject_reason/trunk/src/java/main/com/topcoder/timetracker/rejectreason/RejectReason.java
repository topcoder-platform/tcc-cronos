/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason;

import com.topcoder.timetracker.common.TimeTrackerBean;

/**
 * <p>
 * This bean represents a possible reason why a time or expense entry may be rejected by the Project Manager. Each
 * company has a different set of policies on which entries will be rejected, so each RejectReason is associated with
 * a company.
 * </p>
 *
 * <p>
 * This class is the ValueObject of the DAO pattern..
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This class is mutable, and not thread-safe. Multiple threads are advised to work with their
 * own instance.
 * </p>
 *
 * @author wangqing, TCSDEVELOPER
 * @version 3.2
 */
public class RejectReason extends TimeTrackerBean {

	/**
	 * Automatically generated unique ID for use with serialization.
	 */
	private static final long serialVersionUID = -511387401131858811L;

	/**
     * <p>
     * The id of the company with which this RejectReason is associated with. It will be a positive after set.
     * </p>
     */
    private long companyId = -1;

    /**
     * <p>
     * description of the RejectReason. It may be null when the RejectReason object is initially constructed, but it
     * may not null after initialization by setDescription.
     * </p>
     */
    private String description = null;

    /**
     * <p>
     * Indicates whether the RejectReason is active or not.
     * </p>
     */
    private boolean active = false;

    /**
     * <p>
     * Creates a new RejectReason instance.
     * </p>
     */
    public RejectReason() {
        // your code here
    }

    /**
     * <p>
     * Retrieves the id of the company with which this RejectReason is associated with. And its return value is &gt;=
     * 0.
     * </p>
     *
     * @return the id of the company with which this RejectReason is associated with, it must be &gt;= 0
     */
    public long getCompanyId() {
        return companyId;
    }

    /**
     * <p>
     * Sets the id of the company with which this RejectReason is associated with.
     * </p>
     *
     * @param companyId the id of the company with which this RejectReason is associated with.
     *
     * @throws IllegalArgumentException if the companyId is &lt;=0.
     */
    public void setCompanyId(long companyId) {
        if (companyId <= 0) {
            throw new IllegalArgumentException("The companyId must be a positive integer.");
        }

        if (this.companyId != companyId) {
            this.companyId = companyId;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Retrieves the textual description of the RejectReason. It may be null when the RejectReason object is initially
     * constructed, but it will not be a null or empty String after set.
     * </p>
     *
     * @return the textual description of the RejectReason.
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>
     * Sets the textual description of the RejectReason.
     * </p>
     *
     * @param description textual description of the RejectReason.
     *
     * @throws IllegalArgumentException if description is null or an empty String.
     */
    public void setDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("The description is null.");
        }

        if (description.trim().length() == 0) {
            throw new IllegalArgumentException("The description is empty.");
        }

        if (!description.equals(this.description)) {
            this.description = description;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Retrieves the active status of the RejectReason.
     * </p>
     *
     * @return the active status of the RejectReason.
     */
    public boolean getActive() {
        return active;
    }

    /**
     * <p>
     * Sets the active status of the RejectReason.
     * </p>
     *
     * @param active the active status of the RejectReason.
     */
    public void setActive(boolean active) {
        if (this.active != active) {
            this.active = active;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Indicates whether the RejectReason is active or not.
     * </p>
     *
     * @return true if the RejectReason is active and false otherwise.
     */
    public boolean isActive() {
        return getActive();
    }
}
