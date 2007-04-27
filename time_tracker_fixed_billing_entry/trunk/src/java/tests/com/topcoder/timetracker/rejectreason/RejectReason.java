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
 * Thread Safety: This class is mutable, and not thread-safe. Multiple threads are advised to work with their own
 * instance.
 * </p>
 */
public class RejectReason extends TimeTrackerBean {
    /**
     * <p>
     * The id of the company with which this RejectReason is associated with.
     * </p>
     * 
     * <p>
     * Initialized In: setCompanyId
     * </p>
     * 
     * <p>
     * Modified In: setCompanyId
     * </p>
     * 
     * <p>
     * Accessed In: getCompanyId
     * </p>
     * 
     * <p>
     * Valid Values: after proper initailization, &gt;0
     * </p>
     * 
     * <p></p>
     */
    private long companyId = -1;

    /**
     * <p>
     * description of the RejectReason. It may be null when the RejectReason object is initially constructed, but it
     * may not null after initialization by setDescription.
     * </p>
     * 
     * <p>
     * Initialized In: setDescription
     * </p>
     * 
     * <p>
     * Modified In: setDescription
     * </p>
     * 
     * <p>
     * Accessed In: getDescription
     * </p>
     * 
     * <p>
     * Valid Values: after proper initailization, none null, may empty
     * </p>
     * 
     * <p></p>
     */
    private String description = null;

    /**
     * <p>
     * Indicates whether the RejectReason is active or not.
     * </p>
     * 
     * <p>
     * Initialized In: setActive
     * </p>
     * 
     * <p>
     * Modified In: setActive
     * </p>
     * 
     * <p>
     * Accessed In: getActive, isActive
     * </p>
     * 
     * <p></p>
     */
    private boolean active = false;

    /**
     * <p>
     * m
     * </p>
     */
    public RejectReason() {
        // your code here
    }

    /**
     * <p>
     * Retrieves the id of the company with which this RejectEmail is associated with. And its return value is &gt;= 0.
     * </p>
     *
     * @return the id of the company with which this RejectEmail is associated with, it must be &gt;= 0
     */
    public long getCompanyId() {
        // your code here
        return companyId;
    }

    /**
     * <p>
     * Sets the id of the company with which this RejectReason is associated with.
     * </p>
     * 
     * <p>
     * Implementation Notes: - If the current value that was added is not equal to the previous value, then call
     * setChanged(true).
     * </p>
     *
     * @param companyId the id of the company with which this RejectReason is associated with.
     *
     * @throws IllegalArgumentException if the companyId is &lt;=0.
     */
    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    /**
     * <p>
     * Retrieves the textual description of the RejectReason. It may be null when the RejectReason object is initially
     * constructed, but it may not be set to a null or empty String afterwards.
     * </p>
     *
     * @return the textual description of the RejectReason. (none null, none empty)
     */
    public String getDescription() {
        // your code here
        return null;
    }

    /**
     * <p>
     * Sets the textual description of the RejectReason. It may be null when the RejectReason object is initially
     * constructed, but it may not be set to a null or empty String afterwards.
     * </p>
     * 
     * <p>
     * Implementation Notes: - If the current value that was added is not equal to the previous value, then call
     * setChanged(true).
     * </p>
     * 
     * <p></p>
     *
     * @param description Textual description of the RejectReason.
     *
     * @throws IllegalArgumentException if description is null or an empty String.
     */
    public void setDescription(String description) {
        // your code here
    }

    /**
     * <p>
     * Retrieves the active status of the RejectReason. It may be null when the RejectReason object is initially
     * constructed, but it may not be set to a null afterwards.
     * </p>
     * 
     * <p></p>
     *
     * @return The active status of the RejectReason
     */
    public boolean getActive() {
        // your code here
        return false;
    }

    /**
     * <p>
     * Sets whether the RejectReason is active or not.
     * </p>
     * 
     * <p>
     * Implementation Notes: - If the current value that was added is not equal to the previous value, then call
     * setChanged(true).
     * </p>
     * 
     * <p></p>
     *
     * @param active True if the RejectReason is active; false otherwise.
     */
    public void setActive(boolean active) {
        // your code here
    }

    /**
     * <p>
     * Indicates whether the RejectReason is active or not.
     * </p>
     * 
     * <p>
     * Implementation Notes: call getActive();
     * </p>
     *
     * @return whether the RejectReason is active or not.
     */
    public boolean isActive() {
        // your code here
        return false;
    }

    /**
     * lock-begin
     */
}
